## Weka

### 数据格式
ARFF 文件格式，包含两部分。
* 头部定义。指定所有属性和对应的类型
* 数据区。一行代表一个实例
> 头部定义中的最后一个属性被隐式看做目标变量， <br>
> 缺失数据用问号表示

### 例子

@RELATION zoo

@ATTRIBUTE animal {aardvark,antelope,bass,bear}  <br>
@ATTRIBUTE catsize {false, true}  <br>
@ATTRIBUTE type { mammal, bird, reptile, fish, amphibian, insect, invertebrate }  <br>

@DATA
aardvark,true,mammal <br>
antelope,true,mammal <br>
bass,false,fish <br>
bear,true,mammal <br>

> @RELATION 声明数据集名称
> @ATTRIBUTE 声明属性以及类型
> @DATA 声明实例