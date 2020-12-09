/*
 *  Copyright Ⓒ 2020 Manhattan Associates, Inc.  All Rights Reserved.
 *
 *  Confidential, Proprietary and Trade Secrets Notice
 *
 *  Use of this software is governed by a license agreement. This software
 *  contains confidential, proprietary and trade secret information of
 *  Manhattan Associates, Inc. and is protected under United States and
 *  international copyright and other intellectual property laws. Use, disclosure,
 *  reproduction, modification, distribution, or storage in a retrieval system in
 *  any form or by any means is prohibited without the prior express written
 *  permission of Manhattan Associates, Inc.
 *
 *  Manhattan Associates, Inc.
 *  2300 Windy Ridge Parkway, 10th Floor
 *  Atlanta, GA 30339 USA
 */

package com.assessment.vmware.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskSatus {

    SUCCESS("SUCCESS"), IN_PROGRESS( "IN_PROGRESS"),ERROR("ERROR");

    private String status;
}
