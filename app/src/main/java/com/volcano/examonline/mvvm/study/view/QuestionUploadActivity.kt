package com.volcano.examonline.mvvm.study.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.volcano.examonline.R
import com.volcano.examonline.base.BaseMvvmActivity
import com.volcano.examonline.databinding.ActivityQuestionUploadBinding
import com.volcano.examonline.mvvm.study.model.UploadBean
import com.volcano.examonline.mvvm.study.viewmodel.StudyViewModel
import com.volcano.examonline.util.ToastUtils
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
    private var selectedType: String? = null

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
                    selectedType = "判断题"
                }
                R.id.rb_multi_select -> {
                    mBinding.tvOptionC.visibility = View.VISIBLE
                    mBinding.etOptionC.visibility = View.VISIBLE
                    mBinding.tvOptionD.visibility = View.VISIBLE
                    mBinding.etOptionD.visibility = View.VISIBLE
                    mBinding.tvOptionE.visibility = View.VISIBLE
                    mBinding.etOptionE.visibility = View.VISIBLE
                    selectedType = "多选题"
                }
                else -> {
                    mBinding.tvOptionC.visibility = View.VISIBLE
                    mBinding.etOptionC.visibility = View.VISIBLE
                    mBinding.tvOptionD.visibility = View.VISIBLE
                    mBinding.etOptionD.visibility = View.VISIBLE
                    mBinding.tvOptionE.visibility = View.VISIBLE
                    mBinding.etOptionE.visibility = View.VISIBLE
                    selectedType = "单选题"
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
            if(mBinding.tvQuestionSubject.text == "请选择" || mBinding.tvQuestionLevel.text == "请选择"
                            || mBinding.etQuestionSource.text.isNullOrEmpty() || mBinding.etQuestionKeywords.text.isNullOrEmpty()
                            || mBinding.etQuestionDesc.text.isNullOrEmpty() || selectedType.isNullOrEmpty()) {
                ToastUtils.show(this, "内容不完整，请检查输入！")
            }else {
                val uploadBean = UploadBean(mBinding.tvQuestionSubject.text.toString(), mBinding.etQuestionSource.text.toString(),
                    mBinding.tvQuestionLevel.text.toString(), mBinding.etQuestionKeywords.text.toString(), mBinding.etQuestionDesc.text.toString(), selectedType,
                        null, mBinding.etOptionA.text.toString(), mBinding.etOptionB.text.toString(), mBinding.etOptionC.text.toString(),
                        mBinding.etOptionD.text.toString(), mBinding.etOptionE.text.toString(),
                    mBinding.etQuestionAnswers.text.toString(), mBinding.etQuestionAnalysis.text.toString())
                mViewModel.uploadQuestion(uploadBean)
            }
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
        setDataStatus(mViewModel.liveUploadBean, {
            ToastUtils.show(this, "上传试题失败，请稍后再试！",)
        }, {
            ToastUtils.show(this, "上传试题成功!",)
            finish()
        })
        mViewModel.getSubjects()
    }

}