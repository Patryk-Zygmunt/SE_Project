/**
 * Created by Linus on 23.11.2017.
 */


import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Agent} from "../model/agent";
import {Observable, Subject} from "rxjs";
import {AgentLong} from "../model/agentLong";
import {ServerInfo} from "../model/server_info";
import {Log} from "../model/server_info_interfaces";

@Injectable()
export  class AgentService{

  constructor(private http: HttpClient){
  }

 private url: string ='http://localhost:8081/api/front/';
  // private serverName:string="not chosen";
  private serverNameSource = new Subject<string>();
  serverName$ = this.serverNameSource.asObservable();

  setServerName(name: string) {
    this.serverNameSource.next(name);
  }

  getAllAgentsShortInfo() : Observable<Agent[]>{
   return  this.http.get(this.url+'agents')
            .map(res => res as Agent[]);
  }
  getAgentLongInfo(id:number) : Observable<AgentLong>{
    return  this.http.get(this.url+'agent/'+id)
      .map(res => res as AgentLong);
  }

  getAgentHistory(id:number) : Observable<ServerInfo[]>{
    return  this.http.get(this.url+'agent/'+id)
      .map(res => res as ServerInfo[]);
  }
  getAgentHistoryByPage(id:number,page:number) : Observable<ServerInfo[]>{
    return this.http.get(this.url + 'history/' + id + '/' + page)
      .map(res => res as ServerInfo[]);
  }
  getAgentHistoryByDate(id:number,startDate:number,endDate:number) : Observable<ServerInfo[]>{
    return this.http.get(this.url + 'history/date/' + id + '/' + startDate + '/' + endDate)
      .map(res => res as ServerInfo[]);
  }

  getAgentLogs(id: number, page: number): Observable<Log[]> {
    return this.http.get(this.url + 'logs/' + id + '/' + page)
      .map(res => res as Log[]);
  }


}
