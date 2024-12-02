import { Component, OnInit } from '@angular/core';
import { DashboardData, DashboardService } from '../../services/dashboard.service';
import { MessageService, ConfirmationService } from 'primeng/api';
import { NgModule } from '@angular/core';




import { CardModule } from 'primeng/card';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  imports: [ CardModule ],
  styleUrls: ['./dashboard.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class DashboardComponent implements OnInit {

  dashboardData: DashboardData | null = null;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getDashboardData().subscribe({
      next: (data) => this.dashboardData = data,
      error: (err) => console.error('Erro ao carregar os dados do dashboard:', err)
    });
  }
}
