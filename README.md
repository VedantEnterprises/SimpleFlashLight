A Torch
=========================
A simple flash light app for Android.
It uses your camera's LED flashlight to let you see in the dark.
If you don't have a flashlight with your camera, it use your screen brightness as flash light instead.

Based on :
 [Simple Torch](https://github.com/jomo/SimpleTorch) 
 [simple-led-widget](https://github.com/rahatarmanahmed/simple-led-widget)

##What permissions does this app need?
* Camera

    > Required to access the flashlight.


## How to sign with keystore 
To release this app with your keystore, there are 2 steps involved,
 - Create androidproject.properties file. Look at [androidproject.properties](https://github.com/joielechong/SimpleFlashLight/raw/master/androidproject.properties)
There are 4 properties in it:
    - STORE_FILE, fill this as pointing to your android keystore file
    - STORE_PASSWORD, fill this with your keystore password
    - KEY_ALIAS, fill this with your key alias
    - KEY_PASSWORD, fill this with your alias key password


 - Change AndroidProject.signing property in [gradle.properties](https://github.com/joielechong/SimpleFlashLight/raw/master/gradle.properties) file to your androidproject.properties file path.
 
 NOTE: You must create androidproject.properties file and place your keystore outside this source code root to avoid accidentally commit your keystore to git.

 
## Test Device
Tested on :
-Sony Experia ST26i with Android 4.1.2
-G810C Tablet with Android 4.0.4


Developed By
------------

* Joielechong - <joielechong@rilixtech.com>, [Rilix Technology](www.rilixtech.com)


License
--------

    Copyright 2016 Joielechong, Rilix Technology.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
