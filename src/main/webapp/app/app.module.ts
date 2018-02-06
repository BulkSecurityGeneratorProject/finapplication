import './vendor.ts';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { FinapplicationSharedModule, UserRouteAccessService } from './shared';
import { FinapplicationHomeModule } from './home/home.module';
import { FinapplicationAdminModule } from './admin/admin.module';
import { FinapplicationAccountModule } from './account/account.module';
import { FinapplicationEntityModule } from './entities/entity.module';
import { DashboardModule } from './dashboard/dashboard.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BootstrapSwitchModule} from 'angular2-bootstrap-switch';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        FinapplicationSharedModule,
        FinapplicationHomeModule,
        FinapplicationAdminModule,
        FinapplicationAccountModule,
        FinapplicationEntityModule,
        DashboardModule,
        NgbModule.forRoot(),
        BootstrapSwitchModule.forRoot(),
        BrowserAnimationsModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class FinapplicationAppModule {}
