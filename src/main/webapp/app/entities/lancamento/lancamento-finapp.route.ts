import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LancamentoFinappComponent } from './lancamento-finapp.component';
import { LancamentoFinappDetailComponent } from './lancamento-finapp-detail.component';
import { LancamentoFinappPopupComponent } from './lancamento-finapp-dialog.component';
import { LancamentoFinappDeletePopupComponent } from './lancamento-finapp-delete-dialog.component';

export const lancamentoRoute: Routes = [
    {
        path: 'lancamento-finapp',
        component: LancamentoFinappComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lancamento-finapp/:id',
        component: LancamentoFinappDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lancamentoPopupRoute: Routes = [
    {
        path: 'lancamento-finapp-new',
        component: LancamentoFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lancamento-finapp/:id/edit',
        component: LancamentoFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lancamento-finapp/:id/delete',
        component: LancamentoFinappDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.lancamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
