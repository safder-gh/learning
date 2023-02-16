import { Component,OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit {
ngOnInit():void{
  setTimeout(() => {
    this.title="I have changed after 3 second";
  }, 3000);
}
title ='angular-movies';

}
