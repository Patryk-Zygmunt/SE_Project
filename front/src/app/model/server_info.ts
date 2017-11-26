/**
 * Created by Linus on 23.11.2017.
 */
export class ServerInfo{
  ramTaken:number;
  infoId:number;
  infoTime:Date;

  parseDate():string{
  return  this.infoTime.getDate().toString() + " "+ this.infoTime.getTime().toString();
   //return this.infoTime.dayOfMonth,infoTime.monthValue,infoTime.year,infoTime.hour,infoTime.minute,infoTime.second];


  }


}
