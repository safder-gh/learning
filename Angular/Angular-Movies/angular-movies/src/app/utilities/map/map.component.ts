import { Component, EventEmitter, Output } from '@angular/core';
import {
  latLng,
  LatLng,
  LeafletMouseEvent,
  tileLayer,
  Marker,
  marker,
} from 'leaflet';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { coordinatesMap } from './coordinate';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent {
  @Output()
  onSelectedLocation = new EventEmitter<coordinatesMap>();
  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: 'Angular Movies',
      }),
    ],
    zoom: 5,
    center: latLng(24.860966, 66.990501),
  };
  layers: Marker<any>[] = [];
  handleMapClick(event: LeafletMouseEvent) {
    const latitude = event.latlng.lat;
    const longitude = event.latlng.lng;
    console.log(latitude, longitude);
    this.layers = [];
    this.layers.push(marker([latitude, longitude]));
    this.onSelectedLocation.emit({ latitude, longitude });
  }
}
