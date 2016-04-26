


1. 由于离线推送通知依赖于特定的service，所以集成的时候必须在AndroidManifest.xml的<application> </application>中添加如下配置[已更新过1.8版本配置的，也麻烦改成如下的内容]：

        <!--  消息收发service -->
         <service
            android:name="com.tencent.qalsdk.service.QalService"
            android:exported="false"
            android:process=":QALSERVICE" >
        </service>  
        <!--  离线消息广播接收器 -->
         <receiver
            android:name="com.tencent.qalsdk.QALBroadcastReceiver"
            android:exported="false">
            <intent-filter>
                <action android:name="com.tencent.qalsdk.broadcast.qal" />
            </intent-filter>
        </receiver>
        <!--  系统消息广播接收器 -->
        <receiver 
            android:name="com.tencent.qalsdk.core.NetConnInfoCenter" android:process=":QALSERVICE">
 		    <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
           <intent-filter>
                <action android:name="android.intent.action.TIME_SET" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.TIMEZONE_CHANGED" />
            </intent-filter>
        </receiver> 


2. 如果需要离线推送通知，需要在application的onCreate中调用TIMManager中的setOfflinePushListener接口注册离线推送通知回调（具体参数可以参考相应的javadoc）。如下：
   注意：onCreate() 这里只是初始化离线处理必须的逻辑。app正常启动初始化逻辑建议放在activity中。
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyApplication", "app oncreate");
        if(MsfSdkUtils.isMainProcess(this)) {
            Log.d("MyApplication", "main process");
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    Log.e("MyApplication", "recv offline push");
                    notification.doNotify(getApplicationContext(), R.drawable.ic_launcher);
                }
            });
        }
    }
}






参考文档：

http://www.qcloud.com/doc/product/269/%E6%A6%82%E8%BF%B0%EF%BC%88Android%20SDK%EF%BC%89
