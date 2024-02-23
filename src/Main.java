import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main{
	public static void main(String[] args)throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		int[] DP = new int[N];
		Arrays.fill(DP, Integer.MAX_VALUE);
		StringBuilder sb = new StringBuilder();
		Queue<Node> q = new LinkedList<Node>(); 
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			boolean isCanCalculate = true;
			int struct_num = i;
			int waiting_time = Integer.parseInt(st.nextToken());
			int previous_struct_cnt = st.countTokens()-1;
			System.out.println("previous_struct_cnt: "+previous_struct_cnt);
			if(previous_struct_cnt==0) {
				DP[i] = waiting_time;
			}
			else {
				int maxTime = 0;
				int[] previous_struct_list = new int[previous_struct_cnt];
				for(int cnt = 0;cnt<previous_struct_cnt;cnt++) {
					previous_struct_list[cnt] = Integer.parseInt(st.nextToken());
				}
				System.out.println(Arrays.toString(previous_struct_list));
				for(int cnt = 0;cnt<previous_struct_cnt;cnt++) {
					if(DP[previous_struct_list[cnt]-1]==Integer.MAX_VALUE) {
						isCanCalculate = false;
						break;
					}
				}
				if(isCanCalculate) {
					for(int cnt=0;cnt<previous_struct_cnt;cnt++) {
						maxTime = Math.max(maxTime, DP[previous_struct_list[cnt]-1]);
					}
					DP[i] = maxTime+waiting_time;
				}else {
					System.out.println("AA");
					q.add(new Node(i, previous_struct_list, waiting_time));
					DP[i] = Integer.MAX_VALUE;
				}
			}
		}
		//System.out.println(Arrays.toString(DP));
		
		while(!q.isEmpty()) {
			boolean isCan = true;
			Node curr = q.poll();
			
			//System.out.println(curr);
			int maxTime = 0;
			for(int cnt = 0;cnt<curr.arr.length;cnt++) {
				if(DP[curr.arr[cnt]-1]==Integer.MAX_VALUE) {
					isCan = false;
					break;
				}
			}
			if(isCan) {
				for(int cnt = 0;cnt<curr.arr.length;cnt++) {
					maxTime = Math.max(maxTime, DP[curr.arr[cnt]-1]);
				}
				DP[curr.num] = maxTime+curr.wt;
			}else {
				q.add(curr);
			}
		}
		
		for(int i=0;i<N;i++)
			sb.append(DP[i]+"\n");
		//System.out.println(Arrays.toString(result));
		System.out.println(sb.toString());
	}
	private static class Node{
		int num;
		int[] arr;
		int wt;
		public Node(int num, int[] arr, int wt) {
			this.num = num;
			this.arr = arr;
			this.wt = wt;
		}
		@Override
		public String toString() {
			return "Node [num=" + num + ", arr=" + Arrays.toString(arr) + ", wt=" + wt + "]";
		}
		
	}
}