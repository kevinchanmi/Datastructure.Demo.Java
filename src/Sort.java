/*
Java官方文档:http://hg.openjdk.java.net/jdk7u/jdk7u/jdk/file/55f6804b4352/src/share/classes/java/util/Arrays.java

数据量少，直接用插入排序。
数据量大，不求稳定用快排，要稳定用归并（外排也用）。
理解基数排序思想

复杂极大数据量可以考虑复合排序算法：直接插入+归并排序保证稳定性与速度。
 */

import java.util.Random;
import java.util.Stack;

public class Sort {
    int temp;
    long startTime = 0;
    long endTime = 0;

    public static void main(String[] args) {
        Sort m = new Sort();
        int[] temp = new int[8];
//        m.print_ArrayList(m.dataInt(), "原数组", 0);
        m.bubble_Sort(m.dataInt());
        m.insertion_Sort(m.dataInt());
        m.insertion_Sort2(m.dataInt());
//        m.shell_Sort(m.dataInt());
        m.selection_Sort(m.dataInt());

//        m.heap_Sort(m.dataInt());
        m.mergeSort(m.dataInt());
        //    m.quick_Sort(m.dataInt(),0,m.dataInt().length-1);
        //m.radix_Sort(m.dataInt(), temp, m.dataInt().length, 2, 8, new int[8]);

    }

    public int[] dataInt() {
        int[] data = new int[5000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (int) (Math.random() * 100) - 50;
        }
        return data;
    }


    private void bubble_Sort(int[] a) {//冒泡排序
        startTime = System.currentTimeMillis();
        int i, j, flag = 0;
        for (i = a.length - 1; i >= 0; i--) {
            for (j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                    flag++;
                }
            }
            if (flag == 0) break;
        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "冒泡排序", endTime - startTime);

    }

    private void insertion_Sort(int[] a) {//插入排序
        startTime = System.currentTimeMillis();
        int i, j;
        for (i = 1; i < a.length; i++) {
            temp = a[i];
            for (j = i; j > 0 && a[j - 1] > temp; j--)
                a[j] = a[j - 1];
            a[j] = temp;

        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "插入排序", endTime - startTime);
    }

    private void insertion_Sort2(int[] a) {//插入排序
        startTime = System.currentTimeMillis();
        for (int i = 1; i < a.length ; i++) {
            for (int j = i ; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                } else {         //不需要交换
                    break;
                }
            }
        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "插入排序2", endTime - startTime);
    }

    private void shell_Sort(int[] a) {
        startTime = System.currentTimeMillis();
        int D, i, j, temp;
        for (D = a.length / 2; D > 0; D /= 2) {
            for (i = D; i < a.length; i++) {
                temp = a[i];
                for (j = i; j >= D && a[j - D] > temp; j -= D) {
                    a[j] = a[j - D];
                }
                a[j] = temp;
            }
        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "希尔排序", endTime - startTime);

    }

    private void selection_Sort(int[] a) {
        startTime = System.currentTimeMillis();
        int i, j, min;
        for (i = 0; i < a.length - 1; i++) {
            min = i;
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = a[i];
                a[i] = a[min];
                a[min] = temp;
            }

        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "选择排序", endTime - startTime);
    }

    private void heap_Sort(int[] a) {
        startTime = System.currentTimeMillis();
        //这里元素的索引是从0开始的,所以最后一个非叶子结点array.length/2 - 1
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeap(a, i);  //调整堆
        }

        // 上述逻辑，建堆结束
        // 下面，开始排序逻辑
        for (int j = a.length - 1; j > 0; j--) {
            // 元素交换,作用是去掉大顶堆
            // 把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
            swap(a, 0, j);
            // 元素交换之后，毫无疑问，最后一个元素无需再考虑排序问题了。
            // 接下来我们需要排序的，就是已经去掉了部分元素的堆了，这也是为什么此方法放在循环里的原因
            // 而这里，实质上是自上而下，自左向右进行调整的
            adjustHeap(a, 0);
        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "堆排序", endTime - startTime);

    }

    private void adjustHeap(int[] a, int i) {
        int temp = a[i];
        for (int k = 2 * i + 1; k < a.length; k = 2 * k + 1) {  //2*i+1为左子树i的左子树(因为i是从0开始的),2*k+1为k的左子树
            // 让k先指向子节点中最大的节点
            if (k + 1 < a.length && a[k] < a[k + 1]) {  //如果有右子树,并且右子树大于左子树
                k++;
            }
            //如果发现结点(左右子结点)大于根结点，则进行值的交换
            if (a[k] > temp) {
                swap(a, i, k);
                // 如果子节点更换了，那么，以子节点为根的子树会受到影响,所以，循环对子节点所在的树继续进行判断
                i = k;
            } else {  //不用交换，直接终止循环
                break;
            }
        }
    }

    private void swap(int[] arr, int a, int b) {
        temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private void mergeSort(int[] a) {//非递归方式
        startTime = System.currentTimeMillis();
        int span = 1;//范围初始为1
        while (span < a.length) {
            for (int left = 0; left < a.length; left += 2 * span) {
                int mid = left + span - 1;//中
                if (mid >= a.length - 1) {
                    break;
                }
                int right = left + 2 * span - 1;//右
                if (right >= a.length - 1)
                    right = a.length - 1;
                merge(a, left, mid, right);
            }
            span *= 2;
        }
        endTime = System.currentTimeMillis();
        print_ArrayList(a, "归并排序（非递归）", endTime - startTime);
    }

    private void merge(int[] a, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int leftPoint = left;
        int rightPoint = mid + 1;
        int tmpIndex = 0;
        while (leftPoint <= mid && rightPoint <= right) {
            if (a[leftPoint] < a[rightPoint])
                temp[tmpIndex++] = a[leftPoint++];
            else
                temp[tmpIndex++] = a[rightPoint++];
        }
        while (leftPoint <= mid)
            temp[tmpIndex++] = a[leftPoint++];
        while (rightPoint <= right)
            temp[tmpIndex++] = a[rightPoint++];
        for (int p = 0; p < temp.length; p++) {
            a[left + p] = temp[p];
        }

    }


    private void quick_Sort(int arr[], int start, int end) {
        int pivot = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while ((i < j) && (arr[j] > pivot)) {
                j--;
            }
            while ((i < j) && (arr[i] < pivot)) {
                i++;
            }
            if ((arr[i] == arr[j]) && (i < j)) {
                i++;
            } else {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        if (i - 1 > start) quick_Sort(arr, start, i - 1);
        if (j + 1 < end) quick_Sort(arr, j + 1, end);
        print_ArrayList(arr, "快速排序", 0);
    }

    private void radix_Sort(int[] A, int[] temp, int n, int k, int r, int[] cnt) {//基数排序
        //A:原数组
        //temp:临时数组
        //n:序列的数字个数
        //k:最大的位数2
        //r:基数10
        //cnt:存储bin[i]的个数
        //负数解决方法：遍历所有数据，找出最小值，所有数据加上最小值的绝对值，处理完毕再减去最小值的绝对值。
        startTime = System.currentTimeMillis();
        for (int i = 0, rtok = 1; i < k; i++, rtok = rtok * r) {

            //初始化
            for (int j = 0; j < r; j++) {
                cnt[j] = 0;
            }
            //计算每个箱子的数字个数
            for (int j = 0; j < n; j++) {
                cnt[(A[j] / rtok) % r]++;
            }
            //cnt[j]的个数修改为前j个箱子一共有几个数字
            for (int j = 1; j < r; j++) {
                cnt[j] = cnt[j - 1] + cnt[j];
            }
            for (int j = n - 1; j >= 0; j--) {      //重点理解
                cnt[(A[j] / rtok) % r]--;
                temp[cnt[(A[j] / rtok) % r]] = A[j];
            }
            for (int j = 0; j < n; j++) {
                A[j] = temp[j];
            }
        }
        endTime = System.currentTimeMillis();
        print_ArrayList(A, "基数排序", endTime - startTime);
    }

    private void print_ArrayList(int[] a, String str, long time) {
        System.out.print(str + ":");
//        for (int j : a)
//            System.out.print(j + " ");

        System.out.println("排序所用时间：" + time + "ms");

    }

}
