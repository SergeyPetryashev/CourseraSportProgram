package week2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GreddyAlgorithm {

	public static void main(String[] args) throws IOException {
		// размер суммы
		int [] coin = {10, 5, 2, 1};
		int cost = 29;
		coinCost(coin, cost);
		// задача о расписании
        File file = new File("inputTimeTable.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int tmax = Integer.parseInt(br.readLine()); 
        TimeTable [] tt = new TimeTable[tmax];
        String str="";
        int j=0;
        while((str=br.readLine())!=null) {
        	String [] number =  str.split(" ");
        	tt[j++]=new TimeTable(Integer.parseInt(number[1]), Integer.parseInt(number[0]));
        }
        br.close();
		
		Arrays.sort(tt);
		timetable(tt, tmax);
		// задача о расписании на олимпиаде
		
		
		// задача о выборе заявок
		BufferedReader brReq = new BufferedReader(new FileReader(new File("inputChoiceRequest.txt")));
		int numRequest = Integer.parseInt(brReq.readLine());
		RequestAudience [] reqAudit = new RequestAudience [numRequest];
		List<RequestAudience> reqList = new ArrayList<>();
		for(int i=0; i< numRequest; i++) {
			String [] req = brReq.readLine().split(" ");
			reqAudit[i] = new RequestAudience(Integer.parseInt(req[0]), Integer.parseInt(req[1]));
			reqList.add(reqAudit[i]);
		}
		brReq.close();
		Arrays.sort(reqAudit);
		choiceRequest(reqAudit);
		
		// задача о выборе заявок с бесконечным числом аудиторий
		choiceRequestUnLimit(reqList);
		
		// задача о непрерывном рюкзаке
		System.out.println("task backpack");
		br = new BufferedReader(new FileReader(new File("inputBackPack.txt")));
		String [] data = br.readLine().split(" ");
		int numThings = Integer.parseInt(data[0]);
		int maxWeight = Integer.parseInt(data[1]);
			
		Thing [] things = new Thing [numThings];
		for(int i=0; i< numThings; i++) {
			data = br.readLine().split(" ");
			things[i] = new Thing(Integer.parseInt(data[1]), Integer.parseInt(data[0]));
		}
		br.close();
		Arrays.sort(things);
//		System.out.println(Arrays.toString(things));
		backpack(things, maxWeight);

	}
	
	private static void coinCost(int [] coin, int cost) {
		int numCoin=0;
//		Arrays.sort(coin, );
		for(int i=0; i<coin.length; i++) {
			numCoin+=cost/coin[i];
			cost%=coin[i];
		}
		System.out.println(numCoin);
	}
	
	private static void timetable(TimeTable [] costOrderAndDeadline, int tmax) {
		int [] used = new int [tmax];
		long sum =0;
		for(int i=0; i<costOrderAndDeadline.length; i++) {
			int k=costOrderAndDeadline[i].deadLine;
			while(k>=1 && used[k-1]!=0) {
				k--;
			}
			if(k==0)
				continue;
			used[k-1]=costOrderAndDeadline[i].deadLine;
			sum+=costOrderAndDeadline[i].costOrder;
		}
		
		System.out.println("task timetable");
		System.out.println(sum);
		System.out.println(Arrays.toString(used));
	}
	
	private static void choiceRequest(RequestAudience [] request) {
		List<RequestAudience> choice = new ArrayList<>();
		int cnt=1;
		int last = 0;
		choice.add(request[0]);
		for(int i=1; i< request.length; i++) {
			if(request[i].start>=request[last].end) {
				cnt++;
				last=i;
				choice.add(request[last]);
			}
		}
		System.out.println("task choice request");
		System.out.println(cnt);
		System.out.println(choice);
	}
	
	private static void choiceRequestUnLimit(List<RequestAudience> request) {
		List<RequestAudience> choice;
		int numAud=0;
		while(!request.isEmpty()) {
			numAud++;
			choice = new ArrayList<>();
			choice.add(request.get(0));
			int cnt=1;
			int last = 0;
				
			for(int i=1; i< request.size(); i++) {
				if(request.get(i).start>=request.get(last).end) {
					cnt++;
					last=i;
					choice.add(request.get(last));
				}
			}
			for(RequestAudience ra : choice) {
				request.remove(ra);
			}			
		}
		System.out.println("task choice requestUnLimit");
		System.out.println(numAud);
	}
	
	private static void backpack(Thing [] things, int maxWeight) {
		int i=0;
		int w=0;
		int cnt=0;
		while(w+things[i].weight<=maxWeight) {
			w+=things[i].weight;
			cnt+=things[i].cost;
			i++;
		}
		if(i==things.length-1 && maxWeight-w>things[i].weight) {
			cnt+=things[i].cost;			
			w+=things[i].weight;
		}else if(w<maxWeight) {
			cnt+=(maxWeight-w)*things[i].cost/things[i].weight;			
			w+=(maxWeight-w);
		}
		System.out.println(cnt);
	}
}

class TimeTable implements Comparable<TimeTable>{
	int costOrder;
	int deadLine;
	public TimeTable(int costOrder, int deadLine) {
		this.costOrder=costOrder;
		this.deadLine=deadLine;
	}

	@Override
	public int compareTo(TimeTable o) {
		return Integer.compare(this.costOrder,o.costOrder)*(-1);// reverse
	}
}

class RequestAudience implements Comparable<RequestAudience>{
	int start;
	int end;
	public RequestAudience(int start, int end) {
		this.start=start;
		this.end=end;
	}

	@Override
	public int compareTo(RequestAudience o) {
		return Integer.compare(this.end,o.end);
	}

	@Override
	public String toString() {
		return "[" + start + ", " + end + ")";
	}
}

class Thing implements Comparable<Thing>{
	int cost;
	int weight;
	
	public Thing(int cost, int weight) {
		this.cost=cost;
		this.weight=weight;
	}

	@Override
	public int compareTo(Thing o) {
		return Double.compare(this.cost/(double)this.weight, o.cost/(double)o.weight)*(-1);// reverse;
	}
	@Override
	public String toString() {
		return "[" + cost + ", " + weight + "]";
	}
}