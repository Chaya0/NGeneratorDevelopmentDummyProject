export enum DateFormat {
    DEFAULT = "yyyy-MM-ddTHH:mm:ss.SSSSSS",
    JAVA_DATETIME = "yyyy-MM-ddTHH:mm:ss.SSSSSS",
    DEFAULT_TIME_ONLY="HH:mm"
  }
  
  export class DateUtils {
  
    public static toEndOfDay(date: Date) {
      date.setHours(23);
      date.setMinutes(59);
      date.setSeconds(59);
      return date;
    }
  
    public static toStartOfDay(date: Date) {
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);
      return date;
    }
  
    public static formatDateToString(value: Date | string, format: string | DateFormat = DateFormat.DEFAULT): string {
      const dateObj = typeof value === 'string' ? new Date(value) : value;
      return this.applyFormat(dateObj, format);
    }
  
    private static applyFormat(date: Date, format: string): string {
      const map: { [key: string]: string } = {
        yyyy: date.getFullYear().toString(),
        MM: ('0' + (date.getMonth() + 1)).slice(-2),
        dd: ('0' + date.getDate()).slice(-2),
        HH: ('0' + date.getHours()).slice(-2),
        mm: ('0' + date.getMinutes()).slice(-2),
        ss: ('0' + date.getSeconds()).slice(-2),
        SSSSSS: ('00000' + date.getMilliseconds()).slice(-3) + '000'
      };
  
      // Replace each token in the format string with the corresponding date value
      return format.replace(/yyyy|MM|dd|HH|mm|ss|SSSSSS/g, matched => map[matched]);
    }
  }
  