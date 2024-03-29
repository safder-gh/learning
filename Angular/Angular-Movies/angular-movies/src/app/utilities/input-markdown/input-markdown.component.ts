import { Component, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-input-markdown',
  templateUrl: './input-markdown.component.html',
  styleUrls: ['./input-markdown.component.css']
})
export class InputMarkdownComponent {
  @Input()
  markdownContent:string =''
  @Output()
  changeMarkdown = new EventEmitter<string>();
}
