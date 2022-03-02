//公共js  抛出对象
export default {



     
    //递归方法 去除级联选择器 空白项
    getTreeData(data) {
        // 循环遍历json数据
        for (var i = 0; i < data.length; i++) {

            if (data[i].children.length < 1) {
                // children若为空数组，则将children设为undefined
                data[i].children = undefined;
            } else {
                // children若不为空数组，则继续 递归调用 本方法
                this.getTreeData(data[i].children);
            }
        }
        return data;
    }

}