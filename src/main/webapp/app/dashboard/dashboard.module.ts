import { NgModule } from '@angular/core';
import {CommonModule, DecimalPipe} from '@angular/common';
import { RouterModule } from '@angular/router';
import { DASHBOARD_ROUTE, DashboardComponent, DashboardService} from './';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FinapplicationSharedModule } from '../shared';
import {BootstrapSwitchModule} from 'angular2-bootstrap-switch';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    FinapplicationSharedModule,
    CommonModule,
    NgbModule,
    BootstrapSwitchModule,
    RouterModule.forRoot([DASHBOARD_ROUTE], { useHash: true }),
    NgxChartsModule,
    BrowserAnimationsModule
  ],
  declarations: [DashboardComponent],
  entryComponents: [DashboardComponent],
  providers: [ DashboardService, DecimalPipe ]
})
export class DashboardModule { }
