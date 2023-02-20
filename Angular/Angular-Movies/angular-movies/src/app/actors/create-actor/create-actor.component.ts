import { Component } from '@angular/core';
import { actorCreationDTO } from '../actors.model';

@Component({
  selector: 'app-create-actor',
  templateUrl: './create-actor.component.html',
  styleUrls: ['./create-actor.component.css']
})
export class CreateActorComponent {

  saveChanges(actorCreationDTO:actorCreationDTO){
    console.log(actorCreationDTO)
  }
}
