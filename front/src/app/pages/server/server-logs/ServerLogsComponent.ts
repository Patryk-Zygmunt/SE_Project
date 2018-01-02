import {AgentService} from "../../../service/AgentService";
@Component({
    selector: 'dashboard-main',
    templateUrl: './main_dashboard.html',
  })
  
  export  class DashboardMainComponent {
    agent:AgentLong;
    id:number;
    logs:any[];
  
  
    constructor(private agentService:AgentService) {
    }
  
  
    ngOnInit(){
        this.id = localStorage.getItem("serverId")
        this.agentService.getAgentLongInfo()
        .subscribe(res=> this.agent=res)
      this.agentService.getAgentLogs(0)
        .subscribe( res=>{
            this.logs = res;
          }
        );
    }

nextPage(page:number){
    this.agentService.getAgentLogs(this.id,page)
    .subscribe( res=>{
        this.logs = res;
      }


}


}