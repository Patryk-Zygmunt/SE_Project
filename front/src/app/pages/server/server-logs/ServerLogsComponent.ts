import {AgentService} from "../../../service/AgentService";
import {Component} from "@angular/core";
import {AgentLong} from "../../../model/agentLong";
import {Log} from "../../../model/server_info_interfaces";
@Component({
  selector: 'server-logs',
  templateUrl: './server-logs.html',
  })

export class ServerLogsComponent {
    agent:AgentLong;
    id:number;
  logs: Log[];
  page: number = 0;


  constructor(private agentService: AgentService) {
    }


  ngOnInit() {
    this.id = Number(localStorage.getItem("serverId"));
    this.agentService.getAgentLongInfo(this.id)
        .subscribe(res=> this.agent=res)
    this.agentService.getAgentLogs(this.id, this.page)
        .subscribe( res=>{
            this.logs = res;

          }
        );
    }

  nextPage() {
    this.agentService.getAgentLogs(this.id, ++this.page)
    .subscribe( res=>{
        this.logs = res;
    })
  }

  prevPage() {
    if (this.page > 0) {
      this.agentService.getAgentLogs(this.id, --this.page)
        .subscribe(res => {
          this.logs = res;
        })
    }
  }

}
