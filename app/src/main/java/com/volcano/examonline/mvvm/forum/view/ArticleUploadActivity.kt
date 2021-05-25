package com.volcano.examonline.mvvm.forum.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityArticleUploadBinding
import com.volcano.examonline.mvvm.forum.viewmodel.ArticleUploadViewModel
import com.volcano.examonline.util.ImageLoader
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener
import java.io.File

class ArticleUploadActivity : BaseMvvmActivity<ActivityArticleUploadBinding, ArticleUploadViewModel>() {

    private val items = arrayListOf("生活日常","面试求职","学习心得")
    private val fieldDialog by lazy { CommonDialog(this) }
    var takePhoto = 1
    var fromAlbum = 2
    private var pic: Bitmap? = null
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    private val pictureSelectDialog by lazy { CommonDialog(this) }


    override fun initView() {
        initToolbar()
        mBinding.llArticleField.setOnClickListener {
            fieldDialog.apply {
                show()
                setDatas(items)
                setOnItemClickListener(object : CommonDialogOnItemClickListener {
                    override fun onCLick(item: String) {
                        mBinding.tvArticleType.text = item
                        dismiss()
                    }
                })
            }
        }
        mBinding.tvArticleSelectImg.setOnClickListener {
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
        mBinding.btnArticleEdit.setOnClickListener {
            val title = mBinding.etArticleTitle.text.toString()
            val desc = mBinding.etArticleContent.text.toString()
            if(title == null || title == "" || desc == null || desc == "") {
                Toast.makeText(this, "内容不可为空！", Toast.LENGTH_SHORT).show()
            }else {
                mViewModel.uploadArticle(title, desc, mBinding.tvArticleType.text.toString(),
                        if(pic != null) ImageLoader.bitmap2File(pic!!, outputImage) else null )
            }
        }
    }

    private fun initToolbar() {
        mBinding.toolbarArticleEdit.apply {
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener {
                finish()
            }
            toolbarTitle.text = "发布文章"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            takePhoto -> {
                if(resultCode == Activity.RESULT_OK) {
                    pic = ImageLoader.uri2Bitmap(this, imageUri)
                    mBinding.ivArticleImg.visibility = View.VISIBLE
                    mBinding.ivArticleImg.setImageBitmap(pic)
                }
            }
            fromAlbum -> {
                if(null != data && resultCode == Activity.RESULT_OK) {
                    data.data?.let {uri ->
                        pic = ImageLoader.getBitMapFromUri(this,uri)
                        mBinding.ivArticleImg.visibility = View.VISIBLE
                        mBinding.ivArticleImg.setImageBitmap(pic)
                    }
                }
            }
        }
        if(pictureSelectDialog.isShowing) {
            pictureSelectDialog.dismiss()
        }
    }

    override fun initData() {
        outputImage = File(externalCacheDir, "articleinfo_img.jpg")
        if(outputImage.exists()) {
            outputImage.delete()
        }
        outputImage.createNewFile()
        setDataStatus(mViewModel.uploadArticle, {}, {
            Toast.makeText(this, "发布成功！", Toast.LENGTH_LONG).show()
            finish()
        })
    }

}