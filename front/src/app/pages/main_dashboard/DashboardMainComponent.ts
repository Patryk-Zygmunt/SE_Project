/**
 * Created by Linus on 23.11.2017.
 */
import {Component} from "@angular/core";
import {Agent} from "../../model/agent";
import {AgentService} from "../../service/AgentService";
import {Router} from "@angular/router";

@Component({
  selector: 'dashboard-main',
  templateUrl: './main_dashboard.html',
  styleUrls: ['./dashboard-style.scss'],
})

export class DashboardMainComponent {
  agents: Agent[] = [];
  interval;


  constructor(private agentService: AgentService,
              private router: Router) {
  }


  ngOnInit() {
    this.initData();
    this.interval = setInterval(() => {
      this.agentService.getAllAgentsShortInfo()
        .subscribe(res => {
          console.log("Main");
          this.agents = res;
        })
    }, 50000);
  }

  initData() {
    this.agentService.getAllAgentsShortInfo()
      .subscribe(res => {
          this.agents = res;
          if (this.agents.length > 0 && this.agents.length > 0)
            this.setActualServer(this.agents[0].agentId, this.agents[0].name)
        }
      );
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  setWarnings(status: boolean) {
    if (status)
      return "circle green";
    else
      return "circle red";
  }

  setActualServer(id: number, name: string) {
    localStorage.setItem("serverId", JSON.stringify(id));
    this.agentService.setServerName(name);
  }

  goToServer(id: number, name: string) {
    this.setActualServer(id, name);
    //  localStorage.setItem("serverName", JSON.stringify(name));
    this.router.navigateByUrl('pages/server/' + id);
  }


}
