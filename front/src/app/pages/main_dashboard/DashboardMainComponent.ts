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
})

export  class DashboardMainComponent {
  agents : Agent[] = [];


  constructor(private agentService:AgentService,
              private router: Router) {
  }


  ngOnInit(){
    this.agentService.getAllAgentsShortInfo()
      .subscribe( res=>{
          this.agents = res;
        //if (this.agents.length>0)  localStorage.setItem("serverId",JSON.stringify(this.agents[0].agentId));
        }
      );

  }

  goToServer(id: number, name: string) {
    localStorage.setItem("serverId", JSON.stringify(id));
    this.agentService.setServerName(name);
    //  localStorage.setItem("serverName", JSON.stringify(name));
    this.router.navigateByUrl('pages/server/'+id);
  }






}
