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
export class ServerInfoComponent {

  agent: AgentLong;
  interval;

  constructor(private agentService: AgentService,
              private router: ActivatedRoute) {
  }

  ngOnInit() {
    this.router.params.forEach(params => {
      this.getData(params['id']);
      this.interval = setInterval(() => {
        console.log("onoin");
        this.getData(params['id']);
      }, 5000)
    });
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }


  getData(id: number) {
    this.agentService.getAgentLongInfo(id)
      .subscribe(res => {
        this.agent = res;
      })
  }

  // parseDate(infoTime):string{
  //  // return  infoTime.getDate().toString() + " "+ infoTime.getTime().toString();
  //   return infoTime.hour+":"+infoTime.minute+":"+infoTime.second+"   0" + infoTime.dayOfMonth+"-"+infoTime.monthValue+"-"+infoTime.year;
  // }


}
