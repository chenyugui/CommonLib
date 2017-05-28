# mvplib

包含mvp模式的各基类：

###关系:
- View层(一般是Activity和Fragment)：<br>
&emsp;&emsp;视图层，负责视图的展示处理。视图层创建的时候，同时会创建一个presenter，并把自己跟presenter关联（以接口的形式，每个视图层都会implement一个视图层接口）。<br><br>
&emsp;&emsp;View层基类: MvpBaseActivity、MvpBaseFragment<br>
实现了一些view层通用的方法，例如showTipDialog、showToast。并且写好了在onCreate时创建presenter并关联，在onDestroy时与presenter解除关联。
<br>
&emsp;&emsp;View层接口基类： ViewBaseInterface

- Presenter层: <br>
&emsp;&emsp;逻辑层，负责逻辑业务，在需要操作视图的时候，通过关联了的视图层接口进行操作。<br><br>
&emsp;&emsp;逻辑层基类：MvpBasePresenter<br>
实现了弱引用关联视图、解除关联视图、获取关联的视图的方法



-  Model层<br>
数据层