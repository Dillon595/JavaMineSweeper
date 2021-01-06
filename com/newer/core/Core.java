
package com.newer.core;

import java.util.Random;
import java.util.Scanner;

import com.newer.bean.Grid;
import com.newer.bean.MyPoint;

/**
 * @ClassName: Core
 * @Description: 包含整个扫雷游戏的核心功能：雷区创建、布雷、添加雷数、踩雷
 * @author LYL
 * @date 2021-01-04 10:13:22
 */

public class Core {
	// 定义一个存放雷数的属性
	int count = 10;
	int count2 = 0;//未打开的格子数

	// 定义一个存放格子对象的二维数组
	Grid[][] grid = new Grid[count - 1][count - 1];

	// 定义一个随机数工具属性
	Random r = new Random();

	Scanner sc = new Scanner(System.in);

	// 玩家输入的x，y值
	int x;
	int y;

	public boolean isFinish;
	
	public Core() {
		createGrid();
		setMine();
		setMineNumber();
		show();
		for(;;) {			
			if(isFinish==true) {
				break;
			}
			select();
		}
	}

	// 创建雷区的操作
	public void createGrid() {
		// 通过循环嵌套获取二维数组的每个下标
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// 为当前获取到的每个数组下标设置格子对象
				grid[i][j] = new Grid();
				// 设置当前下标位置的格子对象属性值；
				grid[i][j].setStatus(false);
				grid[i][j].setContent(' ');
			}
		}
	}

	/*
	 * 获取所选点的周围的坐标
	 */
	private MyPoint[] getPoint(int x, int y) {
		// 获取左上角开始顺时针的坐标
		MyPoint[] p = new MyPoint[8];
		p[0] = new MyPoint(x-1,y);
		p[1] = new MyPoint(x-1,y-1);
		p[2] = new MyPoint(x,y-1);
		p[3] = new MyPoint(x+1,y-1);
		p[4] = new MyPoint(x+1,y);
		p[5] = new MyPoint(x+1,y+1);
		p[6] = new MyPoint(x,y+1);
		p[7] = new MyPoint(x-1,y+1);
		
		return p;
		
	}

	// 创建显示雷区的方法
	public void show() {
		
		// 通过循环遍历获取雷区
		for (int i = 0; i < grid.length; i++) {

			// 为每一行每一列添加提示行列数
			if (i == 0) {
				System.out.print("  ");
				for (int y = 0; y < grid.length; y++) {
					System.out.print(y + " ");
				}
				System.out.println();
			}
			System.out.print(i + " ");

			for (int j = 0; j < grid[i].length; j++) {
				
				if (grid[i][j].isStatus()) {
					System.out.print(grid[i][j].getContent() + " ");

				} else {
					System.out.print("■ ");
				}
				
			}
			System.out.println();
		}
		
		/**
		 * 判断是否胜利
		 */
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				if(!grid[i][j].isStatus()) {
					count2=count2+1;
				}
				
					
			}
		}
		//System.out.println("未打开的格子"+count2);
		if(count2==10) {
			System.out.println("游戏胜利~");
			isFinish=true;
			return;
		}
		count2=0;
	}

	/**
	 * @Description: 布雷
	 * @author LYL
	 * @date 2021-01-04 10:44:44
	 */
	public void setMine() {
		int n=count-1;
		// 通过循环操作获取随机位置的布雷
		
		while (count > 0) {
			// 获取行列坐标
			int x = r.nextInt(n);
			int y = r.nextInt(n);
			// 判断当前随机的下标格子对象是否不为雷
			if (grid[x][y].getContent() != '*') {
				// 通过随机获取到的数组下标设置为雷
				grid[x][y].setContent('*');
				// 实现雷数递减
				count--;
			}
//			for(int i=0;i<grid.length;i++) {
//				for(int j=0;j<grid[i].length;j++) {
//					if(i==j) {						
//						grid[i][j].setContent('*');
//						count--;
//					}
//				}
//			}
		}
	}

	/**
	 * 
	 * @Description: 选择坐标
	 * @author LYL
	 * @date 2021-01-04 11:23:07
	 */
	public void select() {
		
		System.out.println("请输入XY轴坐标");
		x = sc.nextInt();
		y = sc.nextInt();
		
		if (x < 0 || x >= grid.length) {
			System.out.println("X轴数据错误请重新输入");
			return;
		} else if (y < 0 || y >= grid.length) {
			System.out.println("Y轴数据错误请重新输入");
			return;
		}
		
		
		if (grid[x][y].getContent() != '*') {
			stampMine(x,y);
			// int[][] surround = getOrientation(y,x);
			show();
		} else if (grid[x][y].getContent() == '*') {
			System.out.println("踩到雷啦~ 游戏结束");
			isFinish = true;
			return;
		}
		
		
		
	}

	private void setMineNumber() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				//普安段当前位置的格子对象是否为雷，如果不是就完成雷数添加
				if(grid[i][j].getContent()!='*') {
					//定义一个雷数统计变量，在每个格子对象获取方向的时候统计周围的雷数
					int num = 0;
					//获取当前格子位置的8个方向坐标对象
					MyPoint[] point = getPoint(i,j);
					//循环遍历坐标对象获取对应方向的格子对象
					for(int k=0;k<point.length;k++) {
						//获取每个坐标对象
						MyPoint p=point[k];
						//判断获取到的方向是否会越界
						if(p.x>=0&&p.y>=0&&p.x<9&&p.y<9) {
							//如果当前方向的格子对象内容为雷则实现雷数统计
							if(grid[p.x][p.y].getContent()=='*') {
								num++;
								
							}
						}
					}
					//判断雷数变量完成雷数添加
					if(num>0) {
						grid[i][j].setContent((char)(48+num));
//						char mount =(char)num;
//						grid[i][j].setContent(mount);
						
					}
					
				}
					
			}
		}
	}
	
	/*
	 * 踩雷的操作
	 */
	public void stampMine(int x,int y) {
		//设置当前格子状态为开启
		grid[x][y].setStatus(true);
		if(grid[x][y].getContent()=='*') {
			System.out.println("踩到雷了~ 游戏结束！");
		}else {
			//如果当前格子内容为空白的时候就需要执行辐射操作
			if(grid[x][y].getContent()==' ') {
				//获取当前空白位置的8个方向
				MyPoint[] point = getPoint(x,y);
				//循环遍历8个方向对象
				for(int i=0;i<point.length;i++) {
					//获取每个坐标对象
					MyPoint p =point[i];
					//判断是否越界
					if(p.x>=0&&p.y>=0&&p.x<9&&p.y<9) {
						//判断当前的坐标格子对象的内容是否为空白，如果是就实现递归调用
						if(grid[p.x][p.y].getContent()==' '&&grid[p.x][p.y].isStatus()==false) {
							stampMine(p.x,p.y);
						}else {
							//如果不是空白而是数字就直接打开
							grid[p.x][p.y].setStatus(true);
						}
					}
				}
			}
		}
	}
}























