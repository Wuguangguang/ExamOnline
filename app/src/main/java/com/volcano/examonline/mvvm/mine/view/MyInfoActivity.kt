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
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityMyInfoBinding
import com.volcano.examonline.mvvm.mine.model.UserInfo
import com.volcano.examonline.mvvm.mine.viewmodel.MineViewModel
import com.volcano.examonline.util.ConstantData
import com.volcano.examonline.util.ImageLoader
import com.volcano.examonline.widget.PictureSelectDialog
import java.io.File

class MyInfoActivity : BaseMvvmActivity<ActivityMyInfoBinding, MineViewModel>() {


    var takePhoto = 1
    var fromAlbum = 2
    private var avatar:Bitmap? = null
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    private val pictureSelectDialog by lazy { PictureSelectDialog(this) }

    override fun initView() {
        window.statusBarColor = resources.getColor(R.color.COLOR_GREY)
        contentView = mBinding.mslMyInfo
        initToolbar()
        mBinding.llUserAvatar.setOnClickListener {
            pictureSelectDialog.show()
            pictureSelectDialog.setTakePhotoListener {
                outputImage = File(externalCacheDir, "output_image.jpg")
                if(outputImage.exists()) {
                    outputImage.delete()
                }
                outputImage.createNewFile()
                imageUri = FileProvider.getUriForFile(this, "com.volcano.examonline.fileprovider", outputImage)
                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, takePhoto)
            }
            pictureSelectDialog.setTakePicListener {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, fromAlbum)
            }
        }
        mBinding.llUserName.setOnClickListener {

        }
        mBinding.llUserAccount.setOnClickListener {

        }
        mBinding.llUserPwd.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            takePhoto -> {
                if(resultCode == Activity.RESULT_OK) {
                    avatar = ImageLoader.uri2Bitmap(this, imageUri)
                    mBinding.ivAvatar.setImageBitmap(ImageLoader.rotateIfRequired(outputImage,avatar!!))
                    mViewModel.setEditFlag()
                }
            }
            fromAlbum -> {
                if(null != data && resultCode == Activity.RESULT_OK) {
                    data.data?.let {uri ->
                        avatar = ImageLoader.getBitMapFromUri(this,uri)
                        mBinding.ivAvatar.setImageBitmap(avatar)
                        mViewModel.setEditFlag()
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
                mBinding.tvUsername.text = it.username
                mBinding.tvUserphone.text = it.phone
                mBinding.tvAccu.text = "${it.accumulate}"
                if(it.avatar != null) {
                    this.avatar = ImageLoader.byteArray2Bitmap(it.avatar!!)
                    mBinding.ivAvatar.setImageBitmap(avatar)
                }
            }
        })
        mViewModel.editFlag.observe(this) {
            if(it == true) {
                mBinding.btnCommit.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val userinfo = UserInfo()
                        userinfo.avatar = ImageLoader.bitmap2String(avatar!!)
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
            refresh()
        })
        refresh()
    }

    private fun refresh() {
        contentView?.showLoading()
        mViewModel.getUserInfo(ConstantData.ID!!)
    }

    override fun doRetry() {
        super.doRetry()
        refresh()
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