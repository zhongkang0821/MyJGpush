package test.bwie.com.myjgpush;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MainActivity extends AppCompatActivity {

    public String tag = "MainActivity";
    public String text;
    public TextView textView;
    private Button btn;
    public Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initRecognizerDialog();
            }
        });
    }

    private void initRecognizerDialog() {
        // 1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, initListener);
        // 2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解//结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        // 3.设置回调接口
        mDialog.setListener(mRecognizerDialogListener);
        // 4.显示dialog，接收语音输入
        mDialog.show();
        text = "";
    }

    InitListener initListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(context, "监听器初始化错误，错误代码=" + code,
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {

        @Override
        public void onResult(RecognizerResult result, boolean isLast) {
            String json = result.getResultString();
            String content = JsonParser.parseIatResult(json);
            text += content;
            Log.d(tag, "###content=" + content);
            textView.setText(text);
        }

        @Override
        public void onError(SpeechError error) {
            Toast.makeText(context, error.getErrorDescription(),
                    Toast.LENGTH_SHORT).show();
        }
    };

}
