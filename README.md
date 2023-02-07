# <p align="center">xBlur</p>
<p align="center">
<img alt="api" src="https://img.shields.io/badge/API-33%2B-green?logo=android"/>
<img alt="api" src="https://img.shields.io/badge/API-33%2B-green?logo=android"/>
<img alt="api" src="https://img.shields.io/badge/API-33%2B-green?logo=android"/>
<img alt="api" src="https://img.shields.io/badge/API-33%2B-green?logo=android"/>
<img alt="api" src="https://img.shields.io/badge/API-33%2B-green?logo=android"/>
</p>
<p align="center">:cloud: xBlur is Realtime iOS-like blur for Android Compose :cloud:</p>

<p align="center"><img src="https://user-images.githubusercontent.com/51151970/210677532-58654512-5f23-40c3-8b66-ed0fcad869a6.png" width="80%"></img></p>


<br>

## :movie_camera: Look how works
<p align="center"><img src="https://user-images.githubusercontent.com/51151970/210706577-6e88ab9d-9f1c-4593-a338-547a9bfc62b0.png" width="50%"></img></p>
<p align="center"><img src="https://user-images.githubusercontent.com/51151970/210678545-aa9103c1-d3f5-4c7d-a0ee-335fda75d8cb.gif" width="50%"></img></p>


https://user-images.githubusercontent.com/51151970/210694618-5910577f-54fc-40cd-9a82-263653fb8fff.mp4


## :rocket: Using Gradle
In your top-level `build.gradle` file:
```gradle
repositories {
  ...
  mavenCentral()
}
```

In your app `build.gradle` file:
```groovy
dependencies {
    implementation 'com.github.x3rocode:xblur-compose:[latest_version]'
}
```

## :zap: Quik How to Use 

```kotlin
//This will look like IOS!
BlurDialog(
      blurRadius = 250,                       //blur radius
      onDismiss = { openDialog = false },     //dialog ondismiss
      size = IntOffset(280,160),              //dialog size
      shape = RoundedCornerShape(30.dp),      //dialog shape
      backgroundColor = Color.White,          //mixing color with dialog
      backgroundColorAlpha = 0.4f,            //mixing color alpha
      dialogDimAmount = 0.3f,                 //set this if you want dark behind dialog.
      dialogBehindBlurRadius = 100,           //blur behind dialog
      isRealtime = true                       //Realtime capture or not. false = only the first time the dialog is captured when it opens.
 ){
  //Some Contents inside dialog     
 }
```

## :fire: Feature

- backgroundColor : You can mix ANY COLOR you want.
<br>

|backgroundColor|sample|
|:----:|:----:|
|backgroundColor = Color.Transparent|<img src="https://user-images.githubusercontent.com/51151970/210683654-ee51199b-1e9b-482c-a743-335a20a47600.png"/>|
|backgroundColor = Color.White|<img src="https://user-images.githubusercontent.com/51151970/210684137-bf3f8c33-30ba-40a3-b40e-e64c8d5355ff.png"/>|
|backgroundColor = Color.Black|<img src="https://user-images.githubusercontent.com/51151970/210684677-744de40e-bb97-4502-b030-5c3306f76a3a.png"/>|
|backgroundColor = Color.Yellow|<img src="https://user-images.githubusercontent.com/51151970/210684888-3c58770e-0330-4532-b5cb-5e9b495b785b.png"/>|
|backgroundColor = Color.Red|<img src="https://user-images.githubusercontent.com/51151970/210685073-9e91ae87-a373-4664-91dd-a5331f5111f8.png"/>|
<br>

- backgroundColorAlpha : You can set background color ALPHA.
<br>

|backgroundColorAlpha|sample|
|:----:|:----:|
|backgroundColorAlpha = 0.0f|<img src="https://user-images.githubusercontent.com/51151970/210683654-ee51199b-1e9b-482c-a743-335a20a47600.png"/>|
|backgroundColorAlpha = 0.5f|<img src="https://user-images.githubusercontent.com/51151970/210685849-378b4e2b-f2fa-402c-b1bc-dff4706651fa.png"/>|
|backgroundColorAlpha = 1.0f|<img src="https://user-images.githubusercontent.com/51151970/210685954-1dea3c22-36be-429d-814e-2777cfcb7de0.png"/>|
<br>

- dialogDimAmount : set this if you want DARK BEHIND dialog.
<br>

|dialogDimAmount = 0.0f|dialogDimAmount = 0.5f|dialogDimAmount = 0.0f|
|:----:|:----:|:----:|
|<img src="https://user-images.githubusercontent.com/51151970/210686408-75080344-5874-47ec-8ede-a464bb5f90a7.png"  width="70%"/>|<img src="https://user-images.githubusercontent.com/51151970/210686490-2b3c23d7-034f-4652-84e1-4ce6ed6991ef.png"  width="70%"/>|<img src="https://user-images.githubusercontent.com/51151970/210686602-b8d4c2bc-8427-4fc6-93ea-bbb1b0a408bf.png"  width="70%"/>|
<br>

- dialogBehindBlurRadius : BLUR BEHIND dialog
<br>

|dialogBehindBlurRadius = 0|dialogBehindBlurRadius = 100|
|:----:|:----:|
|<img src="https://user-images.githubusercontent.com/51151970/210693537-61d9c90a-0cb0-4121-ada7-179c8fbf360c.png"  width="50%"/>|<img src="https://user-images.githubusercontent.com/51151970/210693471-462c2f8d-1d3a-4cf9-9dad-a06a812a1597.png"  width="50%"/>|

<br>

## :alarm_clock: Todo

- blur toast message
- blur navigation bar
- blur bottom sheet

