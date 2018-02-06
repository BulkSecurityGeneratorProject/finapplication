import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CategoriaFinappComponent } from './categoria-finapp.component';
import { CategoriaFinappDetailComponent } from './categoria-finapp-detail.component';
import { CategoriaFinappPopupComponent } from './categoria-finapp-dialog.component';
import { CategoriaFinappDeletePopupComponent } from './categoria-finapp-delete-dialog.component';

@Injectable()
export class CategoriaFinappResolvePagingParams implements Resolve<any> {

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

export const categoriaRoute: Routes = [
    {
        path: 'categoria-finapp',
        component: CategoriaFinappComponent,
        resolve: {
            'pagingParams': CategoriaFinappResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.categoria.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'categoria-finapp/:id',
        component: CategoriaFinappDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.categoria.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoriaPopupRoute: Routes = [
    {
        path: 'categoria-finapp-new',
        component: CategoriaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.categoria.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categoria-finapp/:id/edit',
        component: CategoriaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.categoria.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categoria-finapp/:id/delete',
        component: CategoriaFinappDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.categoria.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
