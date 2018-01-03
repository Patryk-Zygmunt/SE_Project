import {AgentService} from "../../../service/AgentService";
import {Component} from "@angular/core";
import {AgentLong} from "../../../model/agentLong";
@Component({
  selector: 'server-logs',
  templateUrl: './server-logs.html',
  })

export class ServerLogsComponent {
    agent:AgentLong;
    id:number;
    logs:any[];


  constructor(private agentService: AgentService) {
    }


  ngOnInit() {
    this.id = Number(localStorage.getItem("serverId"));
    this.agentService.getAgentLongInfo(this.id)
        .subscribe(res=> this.agent=res)
      this.agentService.getAgentLogs(0)
        .subscribe( res=>{
            this.logs = res;
          }
        );
    }

  /*nextPage(page:number){
    this.agentService.getAgentLogs(this.id,page)
    .subscribe( res=>{
        this.logs = res;
      }


   }*/


}
