package com.example.apple.hun;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkText;


public class RunTimeActivity extends Activity {
	private Button open;
	private EditText key1;
	private ImageView imageView;
	private Button choise;
	private Button encrypt;
	private Button decrypt;
	private Button out;
	private Button noise;
	private Button barchart;
	private Button waterlabel;
    private final int requestCode = 0x101;
	private static int GALLERY_REQUEST_CODE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.runtime);
        waterlabel = (Button) findViewById(R.id.water);
        open = (Button) findViewById(R.id.choose);
        key1=(EditText)findViewById(R.id.key1);
        imageView=(ImageView)findViewById(R.id.imageView);
        choise=(Button)findViewById(R.id.choise);
        encrypt=(Button)findViewById(R.id.encrypt);
        decrypt=(Button)findViewById(R.id.decrypt);
        noise = (Button)findViewById(R.id.noise);
        barchart = (Button)findViewById(R.id.barchart);
        out=(Button)findViewById(R.id.out);
        imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RunTimeActivity.this);
				//builder.setIcon(R.drawable.tools);
				builder.setTitle("图像存储");
				builder.setMessage("是否要保存图片?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								imageView=(ImageView)findViewById(R.id.imageView);
								if(imageView.getDrawable()==null)
								{
									Toast.makeText(RunTimeActivity.this, "请先选择一副图片.", Toast.LENGTH_LONG).show();
									return;
								}
								Bitmap bm1= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
								File tmpDir=new File(Environment.getExternalStorageDirectory().toString()+File.separator+"tumi_de");
								if(!tmpDir.exists()){
									tmpDir.mkdir();
								}
								File img=new File(tmpDir.getAbsolutePath()+"enphoto.png");
								
								try {
									FileOutputStream fos = new FileOutputStream(img);
									bm1.compress(Bitmap.CompressFormat.PNG, 85, fos);
									fos.flush();
									fos.close();
									Toast.makeText(RunTimeActivity.this, "保存成功", Toast.LENGTH_LONG).show();
									return;
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									Toast.makeText(RunTimeActivity.this, "保存失败", Toast.LENGTH_LONG).show();
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();
			}
		});
        choise.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent,GALLERY_REQUEST_CODE);
			}
		});

        waterlabel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                Bitmap backgroundBitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                String waterstring=key1.getText().toString().trim();
                WatermarkText watermarkText = new WatermarkText(waterstring)
                        .setPositionX(0.5) // 横坐标
                        .setPositionY(0.5) // 纵坐标
                        .setTextAlpha(100) // 透明度
                        .setTextColor(Color.WHITE) // 文字水印文字颜色
                        .setTextShadow(0.1f, 5, 5, Color.BLUE); // 字体的阴影


            }
        });

        out.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RunTimeActivity.this);
				//builder.setIcon(R.drawable.tools);
				builder.setTitle("程序关闭");
				builder.setMessage("是否要退出软件?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								RunTimeActivity.this.finish();
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();
			}
		});
        encrypt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				key1 = (EditText)findViewById(R.id.key1);
				imageView=(ImageView)findViewById(R.id.imageView);
				String key1Str=key1.getText().toString().trim();
				if(imageView.getDrawable()==null)
				{
					Toast.makeText(RunTimeActivity.this, "请选择一副图片", Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					if(key1Str.length()==0)
					{
						Toast.makeText(RunTimeActivity.this, "请输入密钥", Toast.LENGTH_LONG).show();
						return;
					}
					else
					{
						//获取输入的密钥
						EditText text=(EditText)findViewById(R.id.key1);
						String str = text.getText().toString();
						double x=Double.valueOf(str);
						if(x>0&&x<1)
						{
							encrypt(x);
							key1.setText("");
						}
						else
						{
							Toast.makeText(RunTimeActivity.this, "密钥应为0~1之间的任意小数(不包括0与1)", Toast.LENGTH_LONG).show();
							return;
						}
					}
				}
			}
		});
        decrypt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				key1 = (EditText)findViewById(R.id.key1);
				imageView=(ImageView)findViewById(R.id.imageView);
				String key1Str=key1.getText().toString().trim();
				if(imageView.getDrawable()==null)
				{
					Toast.makeText(RunTimeActivity.this, "请选择一副图片", Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					if(key1Str.length()==0)
					{
						Toast.makeText(RunTimeActivity.this, "请输入密钥", Toast.LENGTH_LONG).show();
						return;
					}
					else
					{
						//获取输入的密钥
						EditText text=(EditText)findViewById(R.id.key1);
						String str = text.getText().toString();
						double x=Double.valueOf(str);
						if(x>0&&x<1)
						{
							decrypt(x);
							key1.setText("");
						}
						else
						{
							Toast.makeText(RunTimeActivity.this, "密钥应为0~1之间的任意小数(不包括0与1)", Toast.LENGTH_LONG).show();
							return;
						}
					}	
				}
			}
       });
        noise.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				imageView=(ImageView)findViewById(R.id.imageView);
				if(imageView.getDrawable()==null)
				{
					Toast.makeText(RunTimeActivity.this, "请选择一副图片", Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					Toast.makeText(RunTimeActivity.this, "噪声", Toast.LENGTH_LONG).show();
					noise();
					return;
				}
			}
		});

		open.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 2);


			}
		});

            barchart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				Toast.makeText(RunTimeActivity.this, "直方图", Toast.LENGTH_LONG).show();
				imageView=(ImageView)findViewById(R.id.imageView);	
		    	Bitmap bmp= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
				Intent intent=new Intent(RunTimeActivity.this,BarchartActivity.class);
				ByteArrayOutputStream baos=new ByteArrayOutputStream();  
				bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);  
				byte [] bitmapByte =baos.toByteArray();  
				intent.putExtra("bitmap", bitmapByte);  
				startActivity(intent);
			}
        });	
   }

    void encrypt(double x)
    {
    	//获取算法对象
    	myAlgorithms ma=new myAlgorithms();
		ArrayFunctions af=new ArrayFunctions();
		//获取图像像素矩阵的行数与列数
		Bitmap bmp= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
		int M=bmp.getHeight(),N=bmp.getWidth();

		//获取图像像素矩阵
		int[] pixel=new int[M*N];
		bmp.getPixels(pixel, 0, N, 0, 0, N, M);
		//像素矩阵转二维
		int [][]pixels = new int[M][N];
		af.change(pixel, pixels, M, N);
		//进行加密
		ma.encrypt(pixels, x, M, N);
		//加密后矩阵降一维
		af.recovery(pixels, pixel, M, N);
		//生成加密后的图像
		Bitmap bitmap = Bitmap.createBitmap(pixel, 0, N, N, M, Bitmap.Config.ARGB_8888);
    	imageView.setImageBitmap(bitmap);
    	
    }

    private boolean addWatermarkBitmap(Bitmap bitmap,String str,int w,int h) {

        int destWidth = w;   //此处的bitmap已经限定好宽高
        int destHeight = h;
        Log.v("tag","width = " + destWidth+" height = "+destHeight);

        Bitmap icon = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888); //定好宽高的全彩bitmap
        Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); //建立画笔
        photoPaint.setDither(true); //获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);//过滤一些

        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());//创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, destWidth, destHeight);//创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap, src, dst, photoPaint);//将photo 缩放或则扩大到 dst使用的填充区photoPaint

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//设置画笔
        textPaint.setTextSize(destWidth/20);//字体大小
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度
        textPaint.setAntiAlias(true);  //抗锯齿
        textPaint.setStrokeWidth(3);
        textPaint.setAlpha(15);
        textPaint.setStyle(Paint.Style.STROKE); //空心
        textPaint.setColor(Color.WHITE);//采用的颜色
        textPaint.setShadowLayer(1f, 0f, 3f, Color.LTGRAY);
//        textPaint.setShadowLayer(3f, 1, 1,getResources().getColor(android.R.color.white));//影音的设置
        canvas.drawText(str, destWidth/2, destHeight-45, textPaint);//绘制上去字，开始未知x,y采用那只笔绘制
//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();
        bitmap.recycle();
        imageView.setImageBitmap(icon);
        return FileUtil.getInstance().saveMyBitmap(icon,String.valueOf(new Date().getTime())); //保存至文件
//        return true;
    }
    
    void decrypt(double x)
    {
    	//获取算法对象
    	myAlgorithms ma=new myAlgorithms();
		ArrayFunctions af=new ArrayFunctions();
		//获取图像像素矩阵的行数与列数
		Bitmap bmp= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
		int M=bmp.getHeight(),N=bmp.getWidth();
		//获取图像像素矩阵
		int[] pixel=new int[M*N];
		bmp.getPixels(pixel, 0, N, 0, 0, N, M);
		//像素矩阵转二维
		int [][]pixels = new int[M][N];
		af.change(pixel, pixels, M, N);
		//进行加密
		ma.decrypt(pixels, x, M, N);
		//加密后矩阵降一维
		af.recovery(pixels, pixel, M, N);
		//生成加密后的图像
		Bitmap bitmap = Bitmap.createBitmap(pixel, 0, N, N, M, Bitmap.Config.ARGB_8888);
    	imageView.setImageBitmap(bitmap);
    	
    }
    
    //噪声
	 void noise(){
		//获取图像像素矩阵的行数与列数
		Bitmap bmp= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
		int M=bmp.getHeight(),N=bmp.getWidth();

		//获取图像像素矩阵
		int[] pixel=new int[M*N];
		bmp.getPixels(pixel, 0, N, 0, 0, N, M);
		for(int i=0;i<M;++i){
			for(int j=0;j<N&&(i+j*i)<M*N;++j)
				pixel[i+j*i] = 255;
		}
		Bitmap bitmap = Bitmap.createBitmap(pixel, 0, N, N, M, Bitmap.Config.ARGB_8888);
    	imageView.setImageBitmap(bitmap);
	}
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == this.requestCode) {
                String picPath;
                picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
                if (picPath == null || picPath.equals("")) {
                    Toast.makeText(this, R.string.no_choosed_pic, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this,"pic = "+picPath, Toast.LENGTH_SHORT).show();
                Bitmap bm = null;
                try {
                    bm = FileUtil.getInstance().getImage(picPath,imageView.getWidth(),imageView.getHeight()); //获取限定宽高的bitmap，不限定则容易占用内存过大及OOM
                    if (bm == null) {
                        Toast.makeText(this, R.string.no_choosed_pic, Toast.LENGTH_SHORT).show();
                    }else{
                        if (addWatermarkBitmap(bm, key1.getText().toString(), imageView.getWidth(), imageView.getHeight())) {
                            Toast.makeText(this, "水印生成成功，文件已保存在 " + FileUtil.getInstance().IMAGE_PATH, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    if (bm != null) {
                        bm.recycle();
                    }
                    System.gc();
                }
            }
        }

    	if(requestCode == GALLERY_REQUEST_CODE)
    	{
    		if(data == null)
            {
                return;
            }
			if (requestCode == 2) {
				// 从相册返回的数据
				if (data != null) {
					// 得到图片的全路径
					Uri uri = data.getData();
					imageView.setImageURI(uri);
				}
			}
            else
            {
                {
                	Uri uri;
                    uri = data.getData();
					try {
						Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
						ImageView imageView=(ImageView)findViewById(R.id.imageView);
	                	imageView.setImageBitmap(bm);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                }
            }
    	}
    	
    }
}
