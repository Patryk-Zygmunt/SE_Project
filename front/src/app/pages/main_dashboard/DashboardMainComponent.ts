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
    console.log("123")
  }


  ngOnInit(){
    this.agentService.getAllAgentsShortInfo()
      .subscribe( res=>{
          this.agents = res;
        }
      );
  }

  goToServer(id:number) {
    console.log(this.router)
    this.router.navigateByUrl('pages/server/'+id);
  }






}
