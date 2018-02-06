import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TipoContaFinappComponent } from './tipo-conta-finapp.component';
import { TipoContaFinappDetailComponent } from './tipo-conta-finapp-detail.component';
import { TipoContaFinappPopupComponent } from './tipo-conta-finapp-dialog.component';
import { TipoContaFinappDeletePopupComponent } from './tipo-conta-finapp-delete-dialog.component';

@Injectable()
export class TipoContaFinappResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const tipoContaRoute: Routes = [
    {
        path: 'tipo-conta-finapp',
        component: TipoContaFinappComponent,
        resolve: {
            'pagingParams': TipoContaFinappResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.tipoConta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-conta-finapp/:id',
        component: TipoContaFinappDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.tipoConta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoContaPopupRoute: Routes = [
    {
        path: 'tipo-conta-finapp-new',
        component: TipoContaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.tipoConta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-conta-finapp/:id/edit',
        component: TipoContaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.tipoConta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-conta-finapp/:id/delete',
        component: TipoContaFinappDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.tipoConta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
