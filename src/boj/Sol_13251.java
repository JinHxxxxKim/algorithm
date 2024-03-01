package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author JinHxxxxKim
 * 
 * 1. 
 */
public class Sol_13251 {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer st;

	static int M, K;
	static int[] arr;
	
	public static void main(String[] args) throws Exception {
		M = Integer.parseInt(br.readLine().trim());
		arr = new int[M];
		st = new StringTokenizer(br.readLine().trim());
		int total = 0;
		for (int idx = 0; idx < M; ++idx) {
			arr[idx] = Integer.parseInt(st.nextToken());
			total += arr[idx];
		}
		double sum = 0;
		K = Integer.parseInt(br.readLine().trim());
		for (int cnt = 0; cnt < M; ++cnt) {
			double local = 1;
			for (int k = 0; k < K; ++k) {
				local *= (double)(arr[cnt] - k) / (double)(total - k);
			}
			System.out.println(local);
			sum += local;

		}
	
		System.out.println(sum);
	}
}
