package com.volcano.examonline.mvvm.mine.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityMyInfoBinding
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.util.ImageLoader
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener
import com.volcano.examonline.widget.EditDialog
import java.io.File

class MyInfoActivity : BaseMvvmActivity<ActivityMyInfoBinding, MineViewModel>() {


    var takePhoto = 1
    var fromAlbum = 2
    private var avatar:Bitmap? = null
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    private val pictureSelectDialog by lazy { CommonDialog(this) }
    private val editDialog by lazy { EditDialog(this) }

    override fun initView() {
        window.statusBarColor = resources.getColor(R.color.COLOR_GREY)
        initToolbar()
        initListener()
    }

    private fun initListener() {
        mBinding.llUserAvatar.setOnClickListener {
            pictureSelectDialog.show()
            pictureSelectDialog.setDatas(arrayListOf("拍摄","从手机相册选择"))
            pictureSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
                override fun onCLick(item: String) {
                    when(item) {
                        "拍摄" -> {
                            imageUri = FileProvider.getUriForFile(applicationContext, "com.volcano.examonline.fileprovider", outputImage)
                            val intent = Intent("android.media.action.IMAGE_CAPTURE")
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                            startActivityForResult(intent, takePhoto)
                        }
                        else -> {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            intent.type = "image/*"
                            startActivityForResult(intent, fromAlbum)
                        }
                    }
                }
            })
        }
        mBinding.llUserName.setOnClickListener {
            editDialog.apply {
                show()
                setTitle("修改昵称")
                setContent("昵称： ${mBinding.tvUsername.text}")
                setEtVisibility(View.VISIBLE)
                setSureListener("完成") {
                    if(etContent.isNullOrEmpty()) {
                        Toast.makeText(context, "修改后内容不可为空！", Toast.LENGTH_SHORT).show()
                    }else {
                        mBinding.tvUsername.text = etContent
                        dismiss()
                        mViewModel.setEditFlag()
                    }
                }
                setCancelListener("取消") {
                    cancel()
                    dismiss()
                }
            }
        }
        mBinding.llUserAccount.setOnClickListener {
            editDialog.apply {
                show()
                setTitle("修改用户名")
                setContent("用户名： ${mBinding.tvUserphone.text}")
                setEtVisibility(View.VISIBLE)
                setSureListener("完成") {
                    if(etContent.isNullOrEmpty()) {
                        Toast.makeText(context, "修改后内容不可为空！", Toast.LENGTH_SHORT).show()
                    }else {
                        mBinding.tvUserphone.text = etContent
                        dismiss()
                        mViewModel.setEditFlag()
                    }
                }
                setCancelListener("取消") {
                    cancel()
                    dismiss()
                }
            }
        }
        mBinding.llUserPwd.setOnClickListener {
            val intent = Intent(this, ChgPwdActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            takePhoto -> {
                if(resultCode == Activity.RESULT_OK) {
                    avatar = ImageLoader.uri2Bitmap(this, imageUri)
                    mViewModel.uploadAvatar(outputImage)
                }
            }
            fromAlbum -> {
                if(null != data && resultCode == Activity.RESULT_OK) {
                    data.data?.let {uri ->
                        avatar = ImageLoader.getBitMapFromUri(this,uri)
                        mViewModel.uploadAvatar(ImageLoader.bitmap2File(avatar!!, outputImage))
                    }
                }
            }
        }
        if(pictureSelectDialog.isShowing) {
            pictureSelectDialog.dismiss()
        }
    }


    override fun initData() {
        setDataStatus(mViewModel.liveId, {

        }, {
            if(it != null) {
                outputImage = File(externalCacheDir, "userinfo_${it.phone}_avatar.jpg")
                if(outputImage.exists()) {
                    outputImage.delete()
                }
                outputImage.createNewFile()
                mBinding.tvUsername.text = it.username
                mBinding.tvUserphone.text = it.phone
                mBinding.tvAccu.text = "${it.accumulate}"
                if(it.avatar != null) {
                    Glide.with(this).load(it.avatar).into(mBinding.ivAvatar)
                }
            }
        })
        mViewModel.editFlag.observe(this) {
            if(it == true) {
                mBinding.btnCommit.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val userinfo = UserInfo()
                        userinfo.username = mBinding.tvUsername.text.toString()
                        userinfo.phone = mBinding.tvUserphone.text.toString()
                        mViewModel.editUserInfo(userinfo)
                    }
                }
            }
        }
        setDataStatus(mViewModel.liveUserInfo, {

        }, {
            Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show()
            mViewModel.getUserInfo(ConstantData.ID!!)
        })
        setDataStatus(mViewModel.liveUploadAvatar,{}, {
            Toast.makeText(this, "头像上传成功！", Toast.LENGTH_SHORT).show()
            mBinding.ivAvatar.setImageBitmap(avatar)
        })
        mViewModel.getUserInfo(ConstantData.ID!!)
    }

    private fun initToolbar() {
        mBinding.toolbarUserinfo.apply {
            root.background = getDrawable(R.color.COLOR_GREY)
            toolbarTitle.text = "个人信息"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener { finish() }
        }
    }
}