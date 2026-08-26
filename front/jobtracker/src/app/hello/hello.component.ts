import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../app.config';

interface HelloResponse {
  message: string;
}

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.scss',
})
export class HelloComponent implements OnInit {
  private http = inject(HttpClient);
  private apiUrl = inject(API_URL);

  message = 'Ładuj ładuj...';

  ngOnInit() {
    this.http.get<HelloResponse>(`${this.apiUrl}/hello`).subscribe({
      next: (data) => {
        this.message = data.message;
      },
      error: (err) => {
        console.error(err);
        this.message = 'Nie pykło :(';
      },
    });
  }
}