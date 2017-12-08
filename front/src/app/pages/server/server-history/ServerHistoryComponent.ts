import {AgentService} from "../../../service/AgentService";
import { ServerInfo } from "../../../model/server_info";
import { AgentLong } from "../../../model/agentLong";
import { Component } from "@angular/core";
@Component({
    selector: 'dashboard-main',
    templateUrl: './main_dashboard.html',
  })
  
  export  class DashboardMainComponent {
    agent:AgentLong;
    id:number;
    serverInfo:ServerInfo[];
  
  
    constructor(private agentService:AgentService) {
    }
  
  
    ngOnInit(){
        this.id = parseInt(localStorage.getItem("serverId"));
        this.agentService.getAgentLongInfo(this.id)
        .subscribe(res=> this.agent=res)
      this.agentService.getAgentLogs(0)
        .subscribe( res=>{
           // this.logs = res;
          }
        );
    }

nextPage(page:number){
    this.agentService.getAgentLogs(page)
    .subscribe( res=>{
       // this.logs = res;
      }


}


}