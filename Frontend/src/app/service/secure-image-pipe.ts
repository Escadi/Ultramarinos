import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'secureImage'
})
export class SecureImagePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
