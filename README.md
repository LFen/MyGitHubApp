# MyGitHubApp 

## 功能介绍：
1. ***首页*** ： 展示Github上 start > 1000 的前20个Repo, 降序排列
   - 点击item，跳转至Repo详情页
   - 点击搜索按钮，跳转至搜索页
   - 点击TopAppBar按钮：
     - 在未登录状态下，会进入Oauth认证流程
     - 在登录状态下，可以进入用户Repo列表页以及登出操作
2. ***Repo详情页*** : 展示具体某个repo的信息
3. ***Repo搜索页*** : 按照编程语言搜索，按降序排列，展示前20个数据；点击item，可以跳转至对应Repo详情页
4. ***用户Repo列表页*** : 展示已登录用户名下所有Repo列表，点击item，可以跳转至对应Repo详情页
5. 支持使用Cucumber测试

## 技术实现：
1. 使用GitHub RESTful API
2. 使用Kotlin, API level 29+
3. 使用Jetpack Compose UI，以及 Android Architect Component
4. 用户认证信息持久化保存

[ 项目架构图 ]( https://github.com/LFen/MyGitHubApp/blob/main/architecture_diagram.png "project_architecture" )

[ Oauth认证时序图 ]( https://github.com/LFen/MyGitHubApp/blob/main/oauth_sequence.png "oauth_sequence" )
