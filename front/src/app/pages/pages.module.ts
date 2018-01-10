import {NgModule} from "@angular/core";

import {PagesComponent} from "./pages.component";
import {PagesRoutingModule} from "./pages-routing.module";
import {ThemeModule} from "../@theme/theme.module";
import {DashboardMainComponent} from "./main_dashboard/DashboardMainComponent";
//import {ServerModule} from "./server/server.module";
import {ServerInfoComponent} from "./server/server_info_card/ServerInfoComponent";
import {ServerLogsComponent} from "./server/server-logs/ServerLogsComponent";
import {ServerHistoryComponent} from "./server/server-history/ServerHistoryComponent";
import {AgentService} from "../service/AgentService";
import {AngularDateTimePickerModule} from "angular2-datetimepicker";

const PAGES_COMPONENTS = [
  PagesComponent,
];

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    AngularDateTimePickerModule,


  ],
  declarations: [
    ...PAGES_COMPONENTS,
    DashboardMainComponent,
    ServerInfoComponent,
    ServerLogsComponent,
    ServerHistoryComponent
  ],
  providers: [AgentService]
})
export class PagesModule {
}
