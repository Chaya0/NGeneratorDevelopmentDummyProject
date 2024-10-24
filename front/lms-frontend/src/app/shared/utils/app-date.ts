export class AppDate {
  private date!: Date;

  private constructor() {
  }

  static now(): AppDate {
    let appDate = new AppDate();
    appDate.date = new Date();
    return appDate;
  }

  static from(date: Date): AppDate {
    let appDate = new AppDate();
    appDate.date = date;
    return appDate;
  }

  public toEndOfDay() {
    this.date.setHours(23);
    this.date.setMinutes(59);
    this.date.setSeconds(59);
    return this;
  }

  toStartOfDay() {
    this.date.setHours(0);
    this.date.setMinutes(0);
    this.date.setSeconds(0);
    return this;
  }

  public minusDays(dayNumber: number): AppDate {
    this.date.setDate(this.date.getDate() - dayNumber);
    return this;
  }

  public minusMonths(monthNumber: number): AppDate {
    this.date.setMonth(this.date.getMonth() - monthNumber);
    return this;
  }

  public minusYears(yearNumber: number): AppDate {
    this.date.setFullYear(this.date.getFullYear() - yearNumber);
    return this;
  }

  public plusDays(dayNumber: number): AppDate {
    this.date.setDate(this.date.getDate() + dayNumber);
    return this;
  }

  public plusMonths(monthNumber: number): AppDate {
    this.date.setMonth(this.date.getMonth() + monthNumber);
    return this;
  }

  public plusYears(yearNumber: number): AppDate {
    this.date.setFullYear(this.date.getFullYear() + yearNumber);
    return this;
  }

  toString(): string {
    return this.toLocalISOString();
  }

  toDate(): Date {
    return this.date;
  }

  private toLocalISOString(): string {
    const offset = this.date.getTimezoneOffset() * 60000; // Get the offset in milliseconds
    const localDate = new Date(this.date.getTime() - offset); // Adjust the date to local time
    return localDate.toISOString().replace('Z', '')
      ;
  }
}
