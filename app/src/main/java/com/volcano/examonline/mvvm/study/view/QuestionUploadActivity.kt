package com.volcano.examonline.mvvm.study.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.RadioGroup
import androidx.core.content.FileProvider
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityQuestionUploadBinding
import com.volcano.examonline.mvvm.exam.view.ExamActivity
import com.volcano.examonline.mvvm.study.viewmodel.StudyViewModel
import com.volcano.examonline.util.ImageLoader
import com.volcano.examonline.widget.CommonDialog
import com.volcano.examonline.widget.CommonDialogOnItemClickListener
import java.io.File

class QuestionUploadActivity : BaseMvvmActivity<ActivityQuestionUploadBinding, StudyViewModel>() {

    var takePhoto = 1
    var fromAlbum = 2
    private var avatar: Bitmap? = null
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    private val levelSelectDialog by lazy { CommonDialog(this) }
    private val subjectSelectDialog by lazy { CommonDialog(this) }
    private val pictureSelectDialog by lazy { CommonDialog(this) }
    private var subjectNames = arrayListOf<String>()

    override fun initView() {
        initToolbar()
        initListener()
    }

    private fun initListener() {
        mBinding.rgQuestionType.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when(i) {
                R.id.rb_judge -> {
                    mBinding.tvOptionC.visibility = View.GONE
                    mBinding.etOptionC.visibility = View.GONE
                    mBinding.tvOptionD.visibility = View.GONE
                    mBinding.etOptionD.visibility = View.GONE
                    mBinding.tvOptionE.visibility = View.GONE
                    mBinding.etOptionE.visibility = View.GONE
                }
                else -> {
                    mBinding.tvOptionC.visibility = View.VISIBLE
                    mBinding.etOptionC.visibility = View.VISIBLE
                    mBinding.tvOptionD.visibility = View.VISIBLE
                    mBinding.etOptionD.visibility = View.VISIBLE
                    mBinding.tvOptionE.visibility = View.VISIBLE
                    mBinding.etOptionE.visibility = View.VISIBLE
                }
            }
        }
        //选择学科
        mBinding.tvQuestionSubject.setOnClickListener {
            subjectSelectDialog.show()
            subjectSelectDialog.setDatas(subjectNames)
            subjectSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
                override fun onCLick(item: String) {
                    mBinding.tvQuestionSubject.text = item
                    subjectSelectDialog.dismiss()
                }
            })
        }
        //选择难度
        mBinding.tvQuestionLevel.setOnClickListener {
            levelSelectDialog.show()
            levelSelectDialog.setDatas(arrayListOf("简单","中等","困难"))
            levelSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
                override fun onCLick(item: String) {
                    mBinding.tvQuestionLevel.text = item
                    levelSelectDialog.dismiss()
                }
            })
        }
        //选择图片
        mBinding.tvQuestionImg.setOnClickListener {
            pictureSelectDialog.show()
            pictureSelectDialog.setDatas(arrayListOf("拍摄","从手机相册选择"))
            pictureSelectDialog.setOnItemClickListener(object : CommonDialogOnItemClickListener {
                override fun onCLick(item: String) {
                    when(item) {
                        "拍摄" -> {
                            outputImage = File(externalCacheDir, "output_image.jpg")
                            if(outputImage.exists()) {
                                outputImage.delete()
                            }
                            outputImage.createNewFile()
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
        //上传提交
        mBinding.btnUploadQuestion.setOnClickListener {

        }
    }


    private fun initToolbar() {
        mBinding.toolbarQuestionUpload.apply {
            toolbarTitle.text = "上传试题"
            toolbarLeftImageBack.setImageResource(R.drawable.ic_black_back)
            toolbarLeftImageBack.setOnClickListener { finish() }
        }
    }

    override fun initData() {
        setDataStatus(mViewModel.subjects, {
        }, {
            if(!it.isNullOrEmpty()) {
                 mViewModel.subjectData.addAll(it)
                it.forEach{
                    subjectNames.add(it.subjectname!!)
                }
            }
        })
        mViewModel.getSubjects()
    }
}