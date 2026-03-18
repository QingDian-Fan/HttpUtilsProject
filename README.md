### 二维码扫描
#### 介绍
- 基于c++的curl库实现的
#### 使用
##### 配置仓库地址
```groovy
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
##### 配置依赖
- 基础依赖
```groovy
  	dependencies {
	       implementation 'com.github.QingDian-Fan:HttpUtilsProject:v1.0.0'
	}
```
- 异步get请求
```kotlin
                private val map = hashMapOf<String, String>()
                map.clear()
                map["cid"] = "294"
                HttpUtils.getInstance()
                    .asyncGetRequest(
                        "https://www.wanandroid.com/lg/todo/listnotdo/0/json/1",
                        null,
                        map
                    ) {
                        Log.e("TAG----->GET", it)
                        tvContent.text = it
                    }
```
- 异步post请求
```kotlin
                map.clear()
                map["username"] = "xxx"
                map["password"] = "xxx"
                HttpUtils.getInstance()
                    .asyncPostRequest("https://www.wanandroid.com/user/login", null, map) {
                        Log.e("TAG----->POST", it)
                        tvContent.text = it
                    }
```