public class Sort {
    int temp;

    public static void main(String[] args) {
        Sort m = new Sort();

        m.print_ArrayList(m.dataInt(), "原数组");
//        m.bubble_Sort(m.dataInt());
//        m.insertion_Sort(m.dataInt());
//        m.shell_Sort(m.dataInt());
        m.selection_Sort(m.dataInt());
        m.heap_Sort(m.dataInt());
        m.mergeSort(m.dataInt());

    }

    public int[] dataInt() {
        return new int[]{13, 2, 18, 19, 22, 4, 5, 3, -5};
    }


    private void bubble_Sort(int[] a) {//冒泡排序
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
        print_ArrayList(a, "冒泡排序");

    }

    private void insertion_Sort(int[] a) {//插入排序
        int i, j;
        for (i = 1; i < a.length; i++) {
            temp = a[i];
            for (j = i; j > 0 && a[j - 1] > temp; j--)
                a[j] = a[j - 1];
            a[j] = temp;

        }
        print_ArrayList(a, "插入排序");
    }

    private void shell_Sort(int[] a) {
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
        print_ArrayList(a, "希尔排序");

    }

    private void selection_Sort(int[] a) {
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
        print_ArrayList(a, "选择排序");
    }

    private void heap_Sort(int[] a) {
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
        print_ArrayList(a, "堆排序");

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
        int span = 1;//范围初始为1
        while (span < a.length) {
            for (int left = 0; left < a.length; left += 2 * span) {
                int mid = left + span - 1;//中
                if (mid >= a.length - 1) {
                    break;
                }
                int right = left + 2 * span - 1;//右
                if (right >= a.length-1)
                    right = a.length-1;
                merge(a, left, mid, right);
            }
            span *= 2;
        }
        print_ArrayList(a,"归并排序（非递归）");
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

    private void print_ArrayList(int[] a, String str) {
        System.out.print(str + ":");
        for (int j : a) System.out.print(j + " ");
        System.out.println("");

    }

}
