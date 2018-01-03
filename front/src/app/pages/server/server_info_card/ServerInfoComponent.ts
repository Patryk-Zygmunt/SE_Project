/**
 * Created by Linus on 23.11.2017.
 */
import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {AgentLong} from "../../../model/agentLong";
import {AgentService} from "../../../service/AgentService";


@Component({
  selector: 'server_info',
  templateUrl: './server_info.html',
})
export  class ServerInfoComponent {

agent:AgentLong;
time:string="";

  constructor(private agentService:AgentService,
              private router: ActivatedRoute) {
  }

  ngOnInit(){
    this.router.params.forEach(params => {
      this.agentService.getAgentLongInfo( params['id'])
        .subscribe(res=>{
          console.log(">>>"+ params['id']);
          console.log(res);
          this.agent = res;
          this.time = this.parseDate(this.agent.serverInfoResponse.infoTime);
          console.log(this.agent);
          //console.log(this.agent.serverInfoResponse.parseDate());
        })
    });
  }

  parseDate(infoTime):string{
   // return  infoTime.getDate().toString() + " "+ infoTime.getTime().toString();
    return infoTime.hour+":"+infoTime.minute+":"+infoTime.second+"   0" + infoTime.dayOfMonth+"-"+infoTime.monthValue+"-"+infoTime.year;

  }





}
