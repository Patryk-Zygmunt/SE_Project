export interface Ram {
  total: number;
  used: number;
}

export interface Processor {
  system: number;
  unused: number;
  user: number;
}

export interface Disc {
  name: string;
  total: number;
  used: number;
}

export interface Operation {
  name: string;
  read: number;
  write: number;
}

export interface IoInterface {
  name: string;
  rec: number;
  trans: number;
}

export interface Log {
  process: string;
  errorDesc: string;
}

export interface InfoTime {
  hour: number;
  minute: number;
  nano: number;
  second: number;
  dayOfMonth: number;
  dayOfWeek: string;
  dayOfYear: number;
  month: string;
  monthValue: number;
  year: number;
}
