import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContaFinappComponent } from './conta-finapp.component';
import { ContaFinappDetailComponent } from './conta-finapp-detail.component';
import { ContaFinappPopupComponent } from './conta-finapp-dialog.component';
import { ContaFinappDeletePopupComponent } from './conta-finapp-delete-dialog.component';

@Injectable()
export class ContaFinappResolvePagingParams implements Resolve<any> {

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

export const contaRoute: Routes = [
    {
        path: 'conta-finapp',
        component: ContaFinappComponent,
        resolve: {
            'pagingParams': ContaFinappResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.conta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'conta-finapp/:id',
        component: ContaFinappDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.conta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contaPopupRoute: Routes = [
    {
        path: 'conta-finapp-new',
        component: ContaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.conta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'conta-finapp/:id/edit',
        component: ContaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.conta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'conta-finapp/:id/delete',
        component: ContaFinappDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.conta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
