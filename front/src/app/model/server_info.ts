import {Disc, InfoTime, IoInterface, Log, Operation, Processor, Ram} from "./server_info_interfaces";
/**
 * Created by Linus on 23.11.2017.
 */
export class ServerInfo {
  infoTime: string;
  mac?: any;
  name?: any;
  temperature: number;
  ram: Ram;
  processor: Processor;
  discs: Disc[];
  operations: Operation[];
  ioInterfaces: IoInterface[];
  logs: Log[];
  infoId: number;


}
