# Android实验五 RecyclerView翻牌游戏
陈浩然 / 2024110203 / 2024级2班

独立Android Studio项目，基于实验四建立Main基线，再在Dev分支实现实验五。

## 运行
JDK17、SDK35、Gradle8.9。Open本目录，运行app。命令：`gradlew.bat assembleDebug testDebugUnitTest connectedDebugAndroidTest`。设备测试需要模拟器。

## 实现
MainActivity的ActionBar提供关于菜单，显示作者与实验信息。CardAdapter继承RecyclerView.Adapter，完整实现onCreateViewHolder/onBindViewHolder/getItemCount；回调使用getBindingAdapterPosition并检查NO_POSITION。绑定时始终重新设置文本、背景、可点击状态、透明度与内容描述，避免复用串牌。

竖屏资源card_count=24、columns=4；横屏card_count=30、columns=6。每个方向均为3的倍数。牌数变化会自动开新局，界面规则已明确说明；同方向Activity重建保存完整牌局。RecyclerView独立滚动，不嵌套ScrollView。

三张牌：三同点数match=6，奖励24分；三同花色match=3，奖励12分；每次翻开扣1分，失配另扣2分，三张全部合上。从零分连续选择三张净分依次为21、9、-5。未凑齐三张时保持选择，再点已选牌免费取消；已匹配牌不能重复操作。reset在原模型实例内重置分数/牌堆。随机剩余无合法三张组合时提示结束。

模型/视图/控制器分离。所有界面文字和牌面字符通过资源引用；App标题为陈浩然2024110203。资料与最高分仅在当前进程内保留。

GitHub为本次用户指定提交平台；没有上传Gitee。分支保护和PR实际情况见docs目录。
