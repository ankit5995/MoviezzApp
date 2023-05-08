import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from "@angular/platform-browser";

@Pipe({
  name: 'safe'
})
export class SafePipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer){}

  transform(movieUrl:any): unknown {
    return this.sanitizer.bypassSecurityTrustResourceUrl(movieUrl);
  }

}
