import {AgentService} from "../../../service/AgentService";
import {Component} from "@angular/core";
import {AgentLong} from "../../../model/agentLong";
import {Log} from "../../../model/server_info_interfaces";
import {ServerInfo} from "../../../model/server_info";

@Component({
  selector: 'server-logs',
  templateUrl: './server-logs.html',
})

export class ServerLogsComponent {
  // agent: AgentLong;
  id: number;
  logs: Log[];
  page: number = 0;
  serverInfo: ServerInfo[];

  constructor(private agentService: AgentService) {
  }


  ngOnInit() {
    this.id = Number(localStorage.getItem("serverId"));
    // this.agentService.getAgentLongInfo(this.id)
    //   .subscribe(res => this.agent = res)
    this.agentService.getAgentHistoryByPage(this.id, this.page)
      .subscribe(res => {this.serverInfo = res});
    // this.agentService.getAgentLogs(this.id, this.page)
    //   .subscribe(res => {
    //       this.logs = res;
    //     }
    //   );
  }

  nextPage() {
    // this.agentService.getAgentLogs(this.id, ++this.page)
    //   .subscribe(res => {
    //     this.logs = res;
    //   })
    this.agentService.getAgentHistoryByPage(this.id, ++this.page)
      .subscribe(res => {this.serverInfo = res})

  }

  reloadData() {
    // this.agentService.getAgentLogs(this.id, this.page)
    //   .subscribe(res => {
    //     this.logs = res;
    //   })
    this.agentService.getAgentHistoryByPage(this.id, this.page)
      .subscribe(res => {this.serverInfo = res})
  }


  prevPage() {
    if (this.page > 0) {
      this.agentService.getAgentHistoryByPage(this.id, --this.page)
      .subscribe(res => {this.serverInfo = res})

      // this.agentService.getAgentLogs(this.id, --this.page)
      //   .subscribe(res => {
      //     this.logs = res;
      //   })
    }
  }
  static parseDate(infoTime): string {
    // return  infoTime.getDate().toString() + " "+ infoTime.getTime().toString();
    return infoTime.hour + ":" + infoTime.minute + ":" + infoTime.second + "   0" + infoTime.dayOfMonth + "-" + infoTime.monthValue + "-" + infoTime.year;
  }

}
