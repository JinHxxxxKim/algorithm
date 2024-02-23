package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author JinHxxxxKim
 * 
 * 1. 정방향 그래프를 위상정렬을 통해 각 정점까지의 최대 거리를 구한다.
 * 		1-1. 도착지까지의 최대 거리를 구한다.
 * 2. 역방향 그래프를 통해 위상정렬을 실행한다.
 * 		2-1. 위상 정렬을 실행하며, 탐색하는 노드까지의 최대거리 + 정방형의 최대 거리 = 최대거리인지 확인한다.
 * 		2-2. 맞다면 해당 간선은 임계경로로 판단, ans+1을 한다.
 */
public class Sol_1948 {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static StringBuilder sb = new StringBuilder();
	private static StringTokenizer st;

	private static int nodeNum; // 정점의 개수
	private static int edgeNum; // 간선의 개수
	private static int startNode; // 시작 정점
	private static int endNode; // 종료 정점
	private static List<Edge>[] adList; // 정방향 인접리스트
	private static List<Edge>[] reverseAdList; // 역방향 인접리스트
	private static int[] inDegree; // 정방향 진입차수
	private static int[] reverseInDegree; // 역방향 진입차수
	private static int[] dist; // 정방향 각 정점까지 최대 거리
	private static int[] reverseDist; // 역방향 각 정점까지 최대거리
	private static int maxDist; // 도착지까지의 최대 경로
	private static int ans;

	public static void main(String[] args) throws Exception {
		nodeNum = Integer.parseInt(br.readLine().trim());
		edgeNum = Integer.parseInt(br.readLine().trim());
		adList = new List[nodeNum + 1];
		reverseAdList = new List[nodeNum + 1];
		inDegree = new int[nodeNum + 1];
		reverseInDegree = new int[nodeNum + 1];
		dist = new int[nodeNum + 1];
		reverseDist = new int[nodeNum + 1];

		for (int node = 1; node < nodeNum + 1; ++node) {
			adList[node] = new ArrayList<>();
			reverseAdList[node] = new ArrayList<>();
		}

		for (int edgeCnt = 0; edgeCnt < edgeNum; ++edgeCnt) {
			st = new StringTokenizer(br.readLine().trim());
			int srcNode = Integer.parseInt(st.nextToken());
			int destNode = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adList[srcNode].add(new Edge(destNode, weight));
			inDegree[destNode]++;
			reverseAdList[destNode].add(new Edge(srcNode, weight));
			reverseInDegree[srcNode]++;
		}

		st = new StringTokenizer(br.readLine().trim());
		startNode = Integer.parseInt(st.nextToken());
		endNode = Integer.parseInt(st.nextToken());

		// 정방향 위상정렬 시작
		Queue<Integer> q = new ArrayDeque<>();
		q.add(startNode);
		while (!q.isEmpty()) {
			int currNode = q.poll();
			// currNode에 연결 된 간선 확인
			for (Edge currEdge : adList[currNode]) {
				int adNode = currEdge.destNode;
				int weight = currEdge.weight;
				inDegree[adNode]--;
				if (inDegree[adNode] == 0) {
					q.offer(adNode);
				}
				// 최대 거리 갱신
				dist[adNode] = Math.max(dist[adNode], dist[currNode] + weight);
			}
		}
		maxDist = dist[endNode];

		// 역방향 위상정렬 시작
		q = new ArrayDeque<>();
		q.add(endNode);
		while (!q.isEmpty()) {
			int currNode = q.poll();
			// currNode에 연결 된 간선 확인
			for (Edge currEdge : reverseAdList[currNode]) {
				int adNode = currEdge.destNode;
				int weight = currEdge.weight;
				reverseInDegree[adNode]--;
				if (reverseInDegree[adNode] == 0) {
					q.offer(adNode);
				}
				// 출발지로부터 해당 정점까지의 최대거리 + 도착지로부터 해당 정점까지의 최대거리 == 최대거리일 경우
				// 해당 간선은 임계경로에 포함되는 간선
				if ((reverseDist[currNode] + weight) + dist[adNode] == maxDist) {
					ans++;
				}
				// 최대 거리 갱신
				reverseDist[adNode] = Math.max(reverseDist[adNode], reverseDist[currNode] + weight);
			}
		}
		System.out.println(maxDist);
		System.out.println(ans);
	}

	// 간선 클래스
	static class Edge {
		int destNode;
		int weight;

		public Edge(int destNode, int weight) {
			super();
			this.destNode = destNode;
			this.weight = weight;
		}
	}
}